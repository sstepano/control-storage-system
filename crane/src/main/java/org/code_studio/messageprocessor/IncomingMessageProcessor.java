package org.code_studio.messageprocessor;

import java.util.List;
import java.util.regex.Pattern;

import org.code_studio.Common;
import org.code_studio.CraneMessageSchedulerTask;
import org.code_studio.controller.ApiServerController;
import org.code_studio.controller.TcpMessageController;
import org.code_studio.controller.WebSocketController;
import org.code_studio.messageflow.LVSDayStartRequest;
import org.code_studio.messageflow.LVSStatusMessage;
import org.code_studio.messageflow.LVSTransferRequest;
import org.code_studio.messageflow.TEST_USTDayEndResponse;
import org.code_studio.messageflow.TEST_USTDayStartResponse;
import org.code_studio.messageflow.TEST_USTRBGResponse;
import org.code_studio.messageflow.TEST_USTRBGUnloadedResponse;
import org.code_studio.messageflow.TEST_USTTransferResponse;
import org.code_studio.model.Coordinate;
import org.code_studio.model.CoordinateType;
import org.code_studio.model.CraneComponent;
import org.code_studio.model.CraneMessage;
import org.code_studio.model.ErrorCode;
import org.code_studio.model.LVSRequestMessageType;
import org.code_studio.model.LVSResponseMessageType;
import org.code_studio.model.ReportPoint;
import org.code_studio.model.StatusCode;
import org.code_studio.model.StorageCompartment;
import org.code_studio.model.SystemComponent;
import org.code_studio.model.USTRequestMessageType;
import org.code_studio.model.USTResponseMessageType;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


public class IncomingMessageProcessor implements MessageProcessor {

	private CraneMessage incomingMessage;
	private CraneMessage outgoingMessage;
	private ApplicationContext ctx;
	private WebSocketController webSocketController;
	private TcpMessageController tcpMessageController;
	private ApiServerController apiServerController;
	
	private Integer craneMessageScheduleSeconds = Integer.parseInt(Common.applicationProperties.getProperty("crane.message.schedule.seconds"));
	private boolean messageflowAutomationDebug = Boolean.valueOf(Common.applicationProperties.getProperty("messageflow.automation.debug"));
	private ThreadPoolTaskScheduler taskScheduler; 
	
	
	public IncomingMessageProcessor(CraneMessage message) {
		this.incomingMessage = message;
		this.outgoingMessage = message;
		this.ctx = Common.getApplicationContext();
		this.webSocketController = this.ctx.getBean(WebSocketController.class);
		this.tcpMessageController = this.ctx.getBean(TcpMessageController.class);
		this.apiServerController = this.ctx.getBean(ApiServerController.class);
		this.taskScheduler = (ThreadPoolTaskScheduler) this.ctx.getBean("taskScheduler");
	}

	@Override
	public MessageProcessor processMessage() {
		handleError();

		Common.log(getClass(),
		       "SENDER "      + (incomingMessage.getSender()        == null ? "" : incomingMessage.getSender()) 
		  + " | RECEIVER "    + (incomingMessage.getReceiver()      == null ? "" : incomingMessage.getReceiver()) 
		  + " | MESSAGETYPE " + (incomingMessage.getMessageType()   == null ? "" : incomingMessage.getMessageType())
		  + " | ORDINAL "     + (incomingMessage.getOrdinalNumber() == null ? "" : incomingMessage.getOrdinalNumber())
		  + " | ERRORCODE "   + (incomingMessage.getErrorCode()     == null ? "" : incomingMessage.getErrorCode())
		  + " | SOURCE "      + (incomingMessage.getSource()        == null ? "" : incomingMessage.getSource())
		  + " | TARGET "      + (incomingMessage.getTarget()        == null ? "" : incomingMessage.getTarget())
		  + " | REPORTPOINT " + (incomingMessage.getReportPoint()   == null ? "" : incomingMessage.getReportPoint())
		  + " | LETYPE "      + (incomingMessage.getLeType()        == null ? "" : incomingMessage.getLeType())
		  + " | STATUSCODE "  + (incomingMessage.getStatusCode()    == null ? "" : incomingMessage.getStatusCode())
		  + " | USERDATA "    + (incomingMessage.getUserData()      == null ? "" : incomingMessage.getUserData())
		  + " | RAWMESSAGE "  + (incomingMessage.getTelegram().getRawData())
		  );

		switch (incomingMessage.getMessageType()) {
			case USTResponseMessageType.DAYSTART -> { }
			case USTResponseMessageType.DAYEND   -> { }
			case USTResponseMessageType.STATUS   -> { }
			case USTResponseMessageType.TRANSFER -> { }
			case USTResponseMessageType.SYNC     -> { }
			
			case USTRequestMessageType.DAYSTART  -> { 
				outgoingMessage.setMessageType(LVSResponseMessageType.START);
				if (incomingMessage.getStatusCode() == StatusCode.OK) {
					Common.setConnectionDayStartEstablished(true); // TODO: Set to true only if there is no error
					new LVSStatusMessage().sendWithClient();
				}
			}
			case USTRequestMessageType.DAYEND    -> { 
				outgoingMessage.setMessageType(LVSResponseMessageType.END);
				Common.setConnectionDayStartEstablished(false);
			}
			case USTRequestMessageType.STATUS    -> { outgoingMessage.setMessageType(LVSResponseMessageType.STATUS); }
			case USTRequestMessageType.LOADER    -> { 
				/** Kada je automatika, 1001 se tek prijavljuje kada dodje plt na 1005, i tu stane i ceka dalje komande **/
				outgoingMessage.setMessageType(LVSResponseMessageType.LOADER);
				webSocketController.sendStatusMessage("R1001OK");
				
				/** GET NEXT PALETTE FOR IMPORT FROM SERVER
				 *  AND SEND VIA sendMessage/IxxyyyzzOK
				 */
				List<String> nextPaletteForStorage = apiServerController.nextPaletteForStorage();
				if (!nextPaletteForStorage.isEmpty()) {
					tcpMessageController.sendMessage("I" + nextPaletteForStorage.getFirst() + "OK");
					//Ovde incomingMessage nema userData, tako da nemamo koordinate palete
					apiServerController.updatePaletteStatusAndCraneQueue(nextPaletteForStorage.getFirst(), "ENTRY", "STORAGE", "ENTRY", "RBGBELT", "OK");
					Common.applicationProperties.setProperty("crane.palette.iswaiting", "false");
				} else {
					Common.applicationProperties.setProperty("crane.palette.iswaiting", "true");
				}
			}
			case USTRequestMessageType.RBGLOADED  -> {
				outgoingMessage.setMessageType(LVSResponseMessageType.RBGLOADED);
				switch (incomingMessage.getReportPoint()) {
					case ReportPoint.RPRGB1 -> {
						webSocketController.sendStatusMessage("RR001OK");
					}
					case ReportPoint.RPRGB2 -> {
						webSocketController.sendStatusMessage("RR002OK");
					}
					default -> { 
						Common.log(getClass(), "USTRequestMessageType.RBG->Unknown report point received: " + incomingMessage.getReportPoint().getId(), "ERROR");
					}
				}
			}
			case USTRequestMessageType.RBGUNLOADED -> { 
				outgoingMessage.setMessageType(LVSResponseMessageType.RBGUNLOADED);
				apiServerController.updatePaletteStatusAndCraneQueue(incomingMessage.getUserData().toString(), "STORAGE", "EXIT", "RBGBELT", "EXIT", "OK");
				
				switch (incomingMessage.getReportPoint()) {
					case ReportPoint.RP5005 -> {
						webSocketController.sendStatusMessage("R5005OK");
					}
					case ReportPoint.RP7005 -> {
						webSocketController.sendStatusMessage("R7005OK");
					}
					default -> { 
						Common.log(getClass(), "USTRequestMessageType.RBGUNLOADED->Unknown report point received: " + incomingMessage.getReportPoint().getId(), "ERROR");
					}
				}
			}
			
			case USTRequestMessageType.COMPLETED -> {
				outgoingMessage.setMessageType(LVSResponseMessageType.COMPLETED); 
				switch (incomingMessage.getReportPoint()) {
					case RP1001 -> { //Ne znam da li ovo dobijamo, ali svakako moramo da odgovorimo na svaku poruku, inace se kran zaglupi ako ne dobije response.
						webSocketController.sendStatusMessage("R1001ER");
					}
					case RP1003 -> { //Kada nema palete na loaderu (1003) a damo komandu za transfer, ovde prijavi 1003 completed i NB (STORAGENOTLOADED). 
						webSocketController.sendStatusMessage("R1003ER");
					}
					case RP5004 -> {
						/**
						 * Get user data from incoming message, which contains RM coordinates.
						 * Create new Coordinates object and convert the above to CRANE coords
						 * Set this as a new target from 5004 to Storage Compartment
						 */
						Coordinate craneCoords = new Coordinate(incomingMessage.getUserData().toString(), CoordinateType.RM).getCraneCoordinates();
						new LVSTransferRequest(ReportPoint.RP5004, new StorageCompartment(craneCoords), ReportPoint.RPRGB1, incomingMessage.getUserData()).sendWithClient();
						apiServerController.updatePaletteStatusAndCraneQueue(incomingMessage.getUserData().toString(), "ENTRY", "STORAGE", "RBGBELT", "RBGFORKS", "OK");
						webSocketController.sendStatusMessage("R5004OK");
					}
					case RPRGB1 -> { // ovde uvek prijavljuje kad stigne na compartment
						webSocketController.sendStatusMessage("R" + incomingMessage.getUserData().toString() + "OK");
						apiServerController.updatePaletteStatusAndCraneQueue(incomingMessage.getUserData().toString(), "ENTRY", "STORAGE", "STORAGE", "NULL", "OK");
					}
					case RP7004 -> {
						Coordinate craneCoords = new Coordinate(incomingMessage.getUserData().toString(), CoordinateType.RM).getCraneCoordinates();
						new LVSTransferRequest(ReportPoint.RP7004, new StorageCompartment(craneCoords), ReportPoint.RPRGB2, incomingMessage.getUserData()).sendWithClient();
						apiServerController.updatePaletteStatusAndCraneQueue(incomingMessage.getUserData().toString(), "ENTRY", "STORAGE", "RBGBELT", "RBGFORKS", "OK");
						webSocketController.sendStatusMessage("R7004OK");
					}
					case RPRGB2 -> { 
						webSocketController.sendStatusMessage("R" + incomingMessage.getUserData().toString() + "OK");
						apiServerController.updatePaletteStatusAndCraneQueue(incomingMessage.getUserData().toString(), "ENTRY", "STORAGE", "STORAGE", "NULL", "OK");
					}
					case RP3005 -> { 
						webSocketController.sendStatusMessage("R3005OK");
						apiServerController.updatePaletteStatusAndCraneQueue(incomingMessage.getUserData().toString(), "STORAGE", "EXIT", "EXIT", "NULL", "OK");
					}
					case RP5005 -> { 
						webSocketController.sendStatusMessage("R5005OK");
					}
					case RP7005 -> {
						webSocketController.sendStatusMessage("R7005OK");
					}
					
					default -> { 
						Common.log(getClass(), "USTRequestMessageType.COMPLETED->Unknown report point received: " + incomingMessage.getReportPoint().getId(), "ERROR");
					}
				}
			}

			case USTRequestMessageType.REMOVAL -> { outgoingMessage.setMessageType(LVSResponseMessageType.REMOVAL); }
			case USTRequestMessageType.SYNC    -> { outgoingMessage.setMessageType(LVSResponseMessageType.SYNC); }
			

			/********************** TEST START **********************/
			case LVSRequestMessageType.DAYSTART -> {
				if (messageflowAutomationDebug) {
					testThreadSleeep();
					new TEST_USTDayStartResponse(incomingMessage.getSource(), incomingMessage.getTarget()).sendWithClient();
				}
			}
			
			case LVSRequestMessageType.DAYEND -> {
				if (messageflowAutomationDebug) {
					testThreadSleeep();
					new TEST_USTDayEndResponse().sendWithClient();
				}
			}
			
			case LVSRequestMessageType.TRANSFER -> {
				if (messageflowAutomationDebug) {
					Common.log(getClass(), "Message Flow Automation Debug is ENABLED", "WARN");
					Pattern rgx = Pattern.compile("00[0-9]{2}[0-9]{2}[0-9]{2}"); // 00xxyyzz
					
	                switch (incomingMessage.getTarget().getId()) {
						// 1003 -> 5004
						case "5004" -> {
							testThreadSleeep();
							new TEST_USTTransferResponse(ReportPoint.RP1003, ReportPoint.RP5004, ReportPoint.RP5004, incomingMessage.getUserData()).sendWithClient();
							Common.applicationProperties.setProperty("crane.palette.iswaiting", "false");
						}
						// 1003 -> 7004
						case "7004" -> {
							testThreadSleeep();
							new TEST_USTTransferResponse(ReportPoint.RP1003, ReportPoint.RP7004, ReportPoint.RP7004, incomingMessage.getUserData()).sendWithClient();
						}						
						// 5004|7004 -> 00xxyyzz
						case String s when rgx.matcher(s).matches() -> {
							/* TODO: ENDLESS LOOP, INVESTIGATE */
							
							Coordinate coords = new Coordinate(incomingMessage.getUserData().toString(), CoordinateType.RM);
							ReportPoint reportPoint = coords.getX() <= 4 ? ReportPoint.RP5004 : ReportPoint.RP7004;
							SystemComponent sourceTarget = reportPoint == ReportPoint.RP5004 ? ReportPoint.RPRGB1 : ReportPoint.RPRGB2;
							
							testThreadSleeep();
							new TEST_USTRBGResponse(reportPoint, sourceTarget, (ReportPoint) sourceTarget, incomingMessage.getUserData()).sendWithClient();
							testThreadSleeep();
							new TEST_USTTransferResponse(sourceTarget, new StorageCompartment(coords.getCraneCoordinates()), (ReportPoint) sourceTarget, incomingMessage.getUserData()).sendWithClient();
						}
						case "3005" -> {
							Coordinate craneCoords = new Coordinate(incomingMessage.getUserData().toString(),  CoordinateType.RM).getCraneCoordinates();
							testThreadSleeep();
							new TEST_USTRBGUnloadedResponse(new StorageCompartment(craneCoords), ReportPoint.RP3005, ReportPoint.RP5005, incomingMessage.getUserData()).sendWithClient();
							testThreadSleeep();
							new TEST_USTTransferResponse(new StorageCompartment(craneCoords), ReportPoint.RP3005, ReportPoint.RP3005, incomingMessage.getUserData()).sendWithClient();
						}	
						default ->  { System.out.println("DEFAULT TEST");}
					}
				}
			}
			/*********************** TEST END ***********************/


			default -> {
				Common.log(getClass(), "<tcpserver><err> Unknown message type received: " + incomingMessage.getMessageType().getId(), "ERROR");
			}
		}

		return this;
	}

	/**
	 * Handles an error for this incoming message type
	 * Also, handles OK error code, but with erroneous Status Code
	 * An example is Transfer request that gets OK errror code, but STORAGEUNUSED Status code, when palette is not loaded yet to the starting point
	 */
	@Override
	public MessageProcessor handleError() {
		switch (incomingMessage.getErrorCode()) {
			case ErrorCode.WRONGID        -> Common.log(getClass(), "MessageProcessor::handleError->ErrorCode.WRONGID", "ERROR");
			case ErrorCode.WRONGORDINALID -> {
				Common.log(getClass(), "MessageProcessor::handleError->ErrorCode.WRONGORDINALID", "ERROR");
				
				switch (incomingMessage.getMessageType()) {
					case USTRequestMessageType.DAYSTART -> {
						//Ovo se vise ne desava otkada su promenili plc na v.10. Sada umesto wrongid za daystart dolazi gomila nekih status poruka
						Common.log(getClass(), "Sending new DAYSTART with next ordinal value: " + Common.getCurrentMessageOrdinalNumber() + 1, "DEBUG");
						new LVSDayStartRequest().sendWithClient();
					}
					
					default -> { 
						Common.log(getClass(), "MessageProcessor::handleError->ErrorCode.WRONGORDINALID::" + incomingMessage.getMessageType()); 
					}
				}
			}
			case ErrorCode.REPEAT -> Common.log(getClass(), "MessageProcessor::handleError->ErrorCode.REPEAT", "ERROR");
			case ErrorCode.OK     -> { } // We get OK when we receive DAYSTART i.e. 
			
			default -> Common.log(getClass(), "MessageProcessor::handleError->UNKNOWN::" + incomingMessage.getErrorCode(), "ERROR");
		}
		
		// We can have OK error code, but erroneus status code
		switch (incomingMessage.getStatusCode()) {
			case StatusCode.REQUESTOK              -> {} 
			case StatusCode.BADPROFILE             -> Common.log(getClass(), "MessageProcessor::handleError->StatusCode.BADPROFILE", "ERROR");
			case StatusCode.STORAGEOCCUPIED        -> Common.log(getClass(), "MessageProcessor::handleError->StatusCode.STORAGEOCCUPIED", "ERROR");
			case StatusCode.STORAGEEMPTY           -> Common.log(getClass(), "MessageProcessor::handleError->StatusCode.STORAGEEMPTY", "ERROR");
			case StatusCode.BADLOADUNIT            -> Common.log(getClass(), "MessageProcessor::handleError->StatusCode.BADLOADUNIT", "ERROR");
			case StatusCode.STORAGEUNUSED          -> Common.log(getClass(), "MessageProcessor::handleError->StatusCode.STORAGEUNUSED", "ERROR");
			case StatusCode.STORAGEUNKNOWN         -> Common.log(getClass(), "MessageProcessor::handleError->StatusCode.STORAGEUNKNOWN", "ERROR");
			case StatusCode.TRANSFERDUPLICATE      -> {
				Common.log(getClass(), "MessageProcessor::handleError->StatusCode.TRANSFERDUPLICATE", "ERROR");

				if (incomingMessage.getMessageType() == USTRequestMessageType.COMPLETED) { // maybe not needed ... TODO: check
					Common.log(getClass(), String.format("Crane rejected transfer message. Message scheduled in %s seconds", craneMessageScheduleSeconds));
					taskScheduler.schedule(new CraneMessageSchedulerTask(incomingMessage), Instant.now().plus(craneMessageScheduleSeconds, ChronoUnit.SECONDS));
				}
			}
			case StatusCode.TRANSFERREQUESTUNKNOWN -> Common.log(getClass(), "MessageProcessor::handleError->StatusCode.TRANSFERREQUESTUNKNOWN", "ERROR");
			default -> Common.log(getClass(), "MessageProcessor::handleError->StatusCode.STATUSUNKNOWN", "WARN");
		}
		
		return this;
	}
	
	@Override
	public String buildResponse() {
		String res = null;
		outgoingMessage.setSender(CraneComponent.LVS1);
		outgoingMessage.setReceiver(CraneComponent.UST1);
		outgoingMessage.setErrorCode(ErrorCode.OK);
		
		/**
		 * Za ove dve vrste error-a ne treba da vracamo nikakav response
		 */
		if (incomingMessage.getErrorCode() != ErrorCode.WRONGORDINALID || incomingMessage.getErrorCode() != ErrorCode.WRONGID) {
			res = outgoingMessage.getTelegram().getRawData();
		}
		
		return res;
	}

	@Override
	public void setNextProcessor(MessageProcessor messageProcessor) {
		messageProcessor.processMessage();
	}
	
	
	/**
	 * TEST METHOD 
	 */
	private void testThreadSleeep() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Common.log(getClass(), e, "ERROR");
		}
	}


}
