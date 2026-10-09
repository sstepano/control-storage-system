$(document).ready( () => {
		
	$('.wh-row-link').on('click', (e)=> {
		let clickedElement = $(e.target);
		
		$.get( "/paletteMapDetail/" + clickedElement.attr('id'), function( data ) {
		    $( "#modalBody" ).html( data );
		});
		
		$('#modalDetail').modal('show');
	});

	jQuery.ajaxSetup({async:false});	
	let arrOccupiedPaletteCodes81 = [];	
	let arrOccupiedPaletteCodes82 = [];
	let arrOccupiedPaletteCodes71 = [];
	let arrOccupiedPaletteCodes72 = [];
	let arrOccupiedPaletteCodes61 = [];
	let arrOccupiedPaletteCodes62 = [];
	let arrOccupiedPaletteCodes51 = [];
	let arrOccupiedPaletteCodes52 = [];
	let arrOccupiedPaletteCodes41 = [];
	let arrOccupiedPaletteCodes42 = [];
	let arrOccupiedPaletteCodes31 = [];	
	let arrOccupiedPaletteCodes32 = [];
	let arrOccupiedPaletteCodes21 = [];
	let arrOccupiedPaletteCodes22 = [];
	let arrOccupiedPaletteCodes11 = [];
	let arrOccupiedPaletteCodes12 = [];

	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "81", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes81.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "82", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes82.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "71", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes71.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "72", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes72.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "61", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes61.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "62", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes62.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "51", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes51.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "52", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes52.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "41", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes41.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "42", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes42.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "31", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes31.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "32", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes32.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "21", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes21.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "22", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes22.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "11", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes11.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "12", function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes12.push(
				{
					"paletteCode" : data.data[i].paletteCode.trim(),
					"statusId"    : data.data[i].statusId,
					"clientId"    : data.data[i].paletteDocument.clientId,
					"clientName"  : data.data[i].paletteDocument.client.name,
					"length"      : data.data[i].length,
					"width"       : data.data[i].width,
					"height"      : data.data[i].height
				}
			);
		}
	});										

	let arrUnoccupiedPalettesNumber = [];	
	let arrOccupiedPalettesNumber = [];
		
	/**
	 * Gets fixed List<Integer> with 3 elements, where: 1st is totalCnt, 2nd is occupiedCnt, 3rd is freeCnt
	 */
	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "81", function( data ) {
		/*$('#totalNo').text(data.data[0]);*/
		arrOccupiedPalettesNumber[0] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[0] = data.data[2];
		$('#81').text(data.data[2]);
		//if(arrUnoccupiedPalettesNumber[0] === 0) {setClass("occupied", "Zauzeto", "#81");}
		//if(arrUnoccupiedPalettesNumber[0] > 0) setClass("unoccupied", "Nije zauzeto", "#81");		
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "82", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[1] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);		
		arrUnoccupiedPalettesNumber[1] = data.data[2];
		$('#82').text(data.data[2]);
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "71", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[2] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[2] = data.data[2];
		$('#71').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "72", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[3] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[3] = data.data[2];
		$('#72').text(data.data[2]);
	});		
	
	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "61", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[4] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[4] = data.data[2];
		$('#61').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "62", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[5] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[5] = data.data[2];
		$('#62').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "51", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[6] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[6] = data.data[2];
		$('#51').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "52", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[7] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[7] = data.data[2];
		$('#52').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "41", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[8] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[8] = data.data[2];
		$('#41').text(data.data[2]);
	});	
	
	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "42", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[9] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[9] = data.data[2];
		$('#42').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "31", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[10] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[10] = data.data[2];
		$('#31').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "32", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[11] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[11] = data.data[2];
		$('#32').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "21", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[12] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[12] = data.data[2];
		$('#21').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "22", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[13] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[13] = data.data[2];
		$('#22').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "11", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[14] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[14] = data.data[2];
		$('#11').text(data.data[2]);
	});	

	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "12", function( data ) {
		/*$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);*/
		arrOccupiedPalettesNumber[15] = data.data[1]
		//$('#occupiedNo').text(data.data[1]);
		arrUnoccupiedPalettesNumber[15] = data.data[2];
		$('#12').text(data.data[2]);
	});	
	

	let occupiedNo = 0;	
	let unoccupiedNo = 0;
	
	for (let i = 0; i < 16; i++) {
		occupiedNo += arrOccupiedPalettesNumber[i];
		unoccupiedNo += arrUnoccupiedPalettesNumber[i];
	}					

	/*let totalNo = columnNumber * 21;
	let occupiedNo = arrOccupiedPaletteCodes.length;
	let unoccupiedNo = totalNo - occupiedNo;
	$('#totalNo').text(totalNo);*/
	$('#occupiedNo1').text(occupiedNo);
	$('#unoccupiedNo1').text(unoccupiedNo);

	let arrTotalPalettesNo81 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo81 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo81 = [];
	
	// set occupied palettes		
	arrOccupiedPaletteCodes81.forEach (e => {
		/*let styleClass = "unoccupied";
		let title = "Nije zauzeto'";
		let selectedEl = '#' + e.paletteCode;
		
		$(selectedEl).removeClass('unoccupied occupied processing locked');*/
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo81[0]++;
			break;
			case '02':
                arrOccupiedPalettesNo81[1]++;
			break;
			case '03':
                arrOccupiedPalettesNo81[2]++;
			break;
			case '04':
                arrOccupiedPalettesNo81[3]++;
			break;
			case '05':
                arrOccupiedPalettesNo81[4]++;
			break;
			case '06':
                arrOccupiedPalettesNo81[5]++;
			break;
			case '07':
                arrOccupiedPalettesNo81[6]++;
			break;
		}
		
	
		/*$(selectedEl).addClass(styleClass);
		$(selectedEl).attr('title', title);
		$(selectedEl).attr('data-status-id', e.statusId);
		$(selectedEl).attr('data-client-id', e.clientId);
		$(selectedEl).attr('data-client-name', e.clientName);
		$(selectedEl).attr('data-length', e.length);
		$(selectedEl).attr('data-width', e.width);
		$(selectedEl).attr('data-height', e.height);*/
	});
	
	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo81[i] = arrTotalPalettesNo81[i] - arrOccupiedPalettesNo81[i];
		
	}		
	
	$('#811').text(arrUnoccupiedPalettesNo81[0]);
    //if(arrUnoccupiedPalettesNo81[0] === 0) setClass("occupied", "Zauzeto", "#811");	
	//if(arrUnoccupiedPalettesNo81[0] > 0) setClass("unoccupied", "Nije zauzeto", "#811");	
	$('#812').text(arrUnoccupiedPalettesNo81[1]);
	//if(arrUnoccupiedPalettesNo81[1] === 0) {setClass("occupied", "Zauzeto", "#812");}
	//if(arrUnoccupiedPalettesNo81[1] > 0) setClass("unoccupied", "Nije zauzeto", "#812");
	$('#813').text(arrUnoccupiedPalettesNo81[2]);
	//if(arrUnoccupiedPalettesNo81[2] === 0) setClass("occupied", "Zauzeto", "#813");
	//if(arrUnoccupiedPalettesNo81[2] > 0) setClass("unoccupied", "Nije zauzeto", "#813");
	$('#814').text(arrUnoccupiedPalettesNo81[3]);
	//if(arrUnoccupiedPalettesNo81[3] === 0) setClass("occupied", "Zauzeto", "#814");
	//if(arrUnoccupiedPalettesNo81[3] > 0) setClass("unoccupied", "Nije zauzeto", "#814");
	$('#815').text(arrUnoccupiedPalettesNo81[4]);
	//if(arrUnoccupiedPalettesNo81[4] === 0) setClass("occupied", "Zauzeto", "#815");
	//if(arrUnoccupiedPalettesNo81[4] > 0) setClass("unoccupied", "Nije zauzeto", "#815");
	$('#816').text(arrUnoccupiedPalettesNo81[5]);
	//if(arrUnoccupiedPalettesNo81[5] === 0) setClass("occupied", "Zauzeto", "#816");
	//if(arrUnoccupiedPalettesNo81[5] > 0) setClass("unoccupied", "Nije zauzeto", "#816");
	$('#817').text(arrUnoccupiedPalettesNo81[6]);
	//if(arrUnoccupiedPalettesNo81[6] === 0) setClass("occupied", "Zauzeto", "#817");
	//if(arrUnoccupiedPalettesNo81[6] > 0) setClass("unoccupied", "Nije zauzeto", "#817");

	
	let arrTotalPalettesNo82 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo82 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo82 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes82.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo82[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo82[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo82[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo82[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo82[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo82[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo82[6]++;
			break;
		}
	
	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo82[i] = arrTotalPalettesNo82[i] - arrOccupiedPalettesNo82[i];
		
	}		

	$('#821').text(arrUnoccupiedPalettesNo82[0]);
	//if(arrUnoccupiedPalettesNo82[0] === 0) setClass("occupied", "Zauzeto", "#821");	
	//if(arrUnoccupiedPalettesNo82[0] > 0) setClass("unoccupied", "Nije zauzeto", "#821");	
	$('#822').text(arrUnoccupiedPalettesNo82[1]);
	//if(arrUnoccupiedPalettesNo82[1] === 0) {setClass("occupied", "Zauzeto", "#822");}
	//if(arrUnoccupiedPalettesNo82[1] > 0) setClass("unoccupied", "Nije zauzeto", "#822");
	$('#823').text(arrUnoccupiedPalettesNo82[2]);
	//if(arrUnoccupiedPalettesNo82[2] === 0) setClass("occupied", "Zauzeto", "#823");
	//if(arrUnoccupiedPalettesNo82[2] > 0) setClass("unoccupied", "Nije zauzeto", "#823");
	$('#824').text(arrUnoccupiedPalettesNo82[3]);
	//if(arrUnoccupiedPalettesNo82[3] === 0) setClass("occupied", "Zauzeto", "#824");
	//if(arrUnoccupiedPalettesNo82[3] > 0) setClass("unoccupied", "Nije zauzeto", "#824");
	$('#825').text(arrUnoccupiedPalettesNo82[4]);
	//if(arrUnoccupiedPalettesNo82[4] === 0) setClass("occupied", "Zauzeto", "#825");
	//if(arrUnoccupiedPalettesNo82[4] > 0) setClass("unoccupied", "Nije zauzeto", "#825");
	$('#826').text(arrUnoccupiedPalettesNo82[5]);
	//if(arrUnoccupiedPalettesNo82[5] === 0) setClass("occupied", "Zauzeto", "#826");
	//if(arrUnoccupiedPalettesNo82[5] > 0) setClass("unoccupied", "Nije zauzeto", "#826");
	$('#827').text(arrUnoccupiedPalettesNo82[6]);
	//if(arrUnoccupiedPalettesNo82[6] === 0) setClass("occupied", "Zauzeto", "#827");
	//if(arrUnoccupiedPalettesNo82[6] > 0) setClass("unoccupied", "Nije zauzeto", "#827");	
	
	
	let arrTotalPalettesNo71 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo71 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo71 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes71.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo71[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo71[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo71[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo71[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo71[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo71[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo71[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo71[i] = arrTotalPalettesNo71[i] - arrOccupiedPalettesNo71[i];
		
	}		

	$('#711').text(arrUnoccupiedPalettesNo71[0]);
	//if(arrUnoccupiedPalettesNo71[0] === 0) setClass("occupied", "Zauzeto", "#711");	
	//if(arrUnoccupiedPalettesNo71[0] > 0) setClass("unoccupied", "Nije zauzeto", "#711");	
	$('#712').text(arrUnoccupiedPalettesNo71[1]);
	//if(arrUnoccupiedPalettesNo71[1] === 0) {setClass("occupied", "Zauzeto", "#712");}
	//if(arrUnoccupiedPalettesNo71[1] > 0) setClass("unoccupied", "Nije zauzeto", "#712");
	$('#713').text(arrUnoccupiedPalettesNo71[2]);
	//if(arrUnoccupiedPalettesNo71[2] === 0) setClass("occupied", "Zauzeto", "#713");
	//if(arrUnoccupiedPalettesNo71[2] > 0) setClass("unoccupied", "Nije zauzeto", "#713");
	$('#714').text(arrUnoccupiedPalettesNo71[3]);
	//if(arrUnoccupiedPalettesNo71[3] === 0) setClass("occupied", "Zauzeto", "#714");
	//if(arrUnoccupiedPalettesNo71[3] > 0) setClass("unoccupied", "Nije zauzeto", "#714");
	$('#715').text(arrUnoccupiedPalettesNo71[4]);
	//if(arrUnoccupiedPalettesNo71[4] === 0) setClass("occupied", "Zauzeto", "#715");
	//if(arrUnoccupiedPalettesNo71[4] > 0) setClass("unoccupied", "Nije zauzeto", "#715");
	$('#716').text(arrUnoccupiedPalettesNo71[5]);
	//if(arrUnoccupiedPalettesNo71[5] === 0) setClass("occupied", "Zauzeto", "#716");
	//if(arrUnoccupiedPalettesNo71[5] > 0) setClass("unoccupied", "Nije zauzeto", "#716");
	$('#717').text(arrUnoccupiedPalettesNo71[6]);
	//if(arrUnoccupiedPalettesNo71[6] === 0) setClass("occupied", "Zauzeto", "#717");
	//if(arrUnoccupiedPalettesNo71[6] > 0) setClass("unoccupied", "Nije zauzeto", "#717");	
	
	
	let arrTotalPalettesNo72 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo72 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo72 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes72.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo72[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo72[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo72[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo72[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo72[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo72[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo72[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo72[i] = arrTotalPalettesNo72[i] - arrOccupiedPalettesNo72[i];
		
	}		

	$('#721').text(arrUnoccupiedPalettesNo72[0]);
	//if(arrUnoccupiedPalettesNo72[0] === 0) setClass("occupied", "Zauzeto", "#721");	
	//if(arrUnoccupiedPalettesNo72[0] > 0) setClass("unoccupied", "Nije zauzeto", "#721");	
	$('#722').text(arrUnoccupiedPalettesNo72[1]);
	//if(arrUnoccupiedPalettesNo72[1] === 0) {setClass("occupied", "Zauzeto", "#722");}
	//if(arrUnoccupiedPalettesNo72[1] > 0) setClass("unoccupied", "Nije zauzeto", "#722");
	$('#723').text(arrUnoccupiedPalettesNo72[2]);
	//if(arrUnoccupiedPalettesNo72[2] === 0) setClass("occupied", "Zauzeto", "#723");
	//if(arrUnoccupiedPalettesNo72[2] > 0) setClass("unoccupied", "Nije zauzeto", "#723");
	$('#724').text(arrUnoccupiedPalettesNo72[3]);
	//if(arrUnoccupiedPalettesNo72[3] === 0) setClass("occupied", "Zauzeto", "#724");
	//if(arrUnoccupiedPalettesNo72[3] > 0) setClass("unoccupied", "Nije zauzeto", "#724");
	$('#725').text(arrUnoccupiedPalettesNo72[4]);
	//if(arrUnoccupiedPalettesNo72[4] === 0) setClass("occupied", "Zauzeto", "#725");
	//if(arrUnoccupiedPalettesNo72[4] > 0) setClass("unoccupied", "Nije zauzeto", "#725");
	$('#726').text(arrUnoccupiedPalettesNo72[5]);
	//if(arrUnoccupiedPalettesNo72[5] === 0) setClass("occupied", "Zauzeto", "#726");
	//if(arrUnoccupiedPalettesNo72[5] > 0) setClass("unoccupied", "Nije zauzeto", "#726");
	$('#727').text(arrUnoccupiedPalettesNo72[6]);
	//if(arrUnoccupiedPalettesNo72[6] === 0) setClass("occupied", "Zauzeto", "#727");
	//if(arrUnoccupiedPalettesNo72[6] > 0) setClass("unoccupied", "Nije zauzeto", "#727");	
	
	
	let arrTotalPalettesNo61 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo61 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo61 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes61.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo61[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo61[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo61[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo61[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo61[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo61[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo61[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo61[i] = arrTotalPalettesNo61[i] - arrOccupiedPalettesNo61[i];
		
	}		

	$('#611').text(arrUnoccupiedPalettesNo61[0]);
	//if(arrUnoccupiedPalettesNo61[0] === 0) setClass("occupied", "Zauzeto", "#611");	
	//if(arrUnoccupiedPalettesNo61[0] > 0) setClass("unoccupied", "Nije zauzeto", "#611");	
	$('#612').text(arrUnoccupiedPalettesNo61[1]);
	//if(arrUnoccupiedPalettesNo61[1] === 0) {setClass("occupied", "Zauzeto", "#612");}
	//if(arrUnoccupiedPalettesNo61[1] > 0) setClass("unoccupied", "Nije zauzeto", "#612");
	$('#613').text(arrUnoccupiedPalettesNo61[2]);
	//if(arrUnoccupiedPalettesNo61[2] === 0) setClass("occupied", "Zauzeto", "#613");
	//if(arrUnoccupiedPalettesNo61[2] > 0) setClass("unoccupied", "Nije zauzeto", "#613");
	$('#614').text(arrUnoccupiedPalettesNo61[3]);
	//if(arrUnoccupiedPalettesNo61[3] === 0) setClass("occupied", "Zauzeto", "#614");
	//if(arrUnoccupiedPalettesNo61[3] > 0) setClass("unoccupied", "Nije zauzeto", "#614");
	$('#615').text(arrUnoccupiedPalettesNo61[4]);
	//if(arrUnoccupiedPalettesNo61[4] === 0) setClass("occupied", "Zauzeto", "#615");
	//if(arrUnoccupiedPalettesNo61[4] > 0) setClass("unoccupied", "Nije zauzeto", "#615");
	$('#616').text(arrUnoccupiedPalettesNo61[5]);
	//if(arrUnoccupiedPalettesNo61[5] === 0) setClass("occupied", "Zauzeto", "#616");
	//if(arrUnoccupiedPalettesNo61[5] > 0) setClass("unoccupied", "Nije zauzeto", "#616");
	$('#617').text(arrUnoccupiedPalettesNo61[6]);
	//if(arrUnoccupiedPalettesNo61[6] === 0) setClass("occupied", "Zauzeto", "#617");
	//if(arrUnoccupiedPalettesNo61[6] > 0) setClass("unoccupied", "Nije zauzeto", "#617");	
	
	
	let arrTotalPalettesNo62 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo62 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo62 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes62.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo62[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo62[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo62[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo62[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo62[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo62[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo62[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo62[i] = arrTotalPalettesNo62[i] - arrOccupiedPalettesNo62[i];
		
	}		

	$('#621').text(arrUnoccupiedPalettesNo62[0]);
	//if(arrUnoccupiedPalettesNo62[0] === 0) setClass("occupied", "Zauzeto", "#621");	
	//if(arrUnoccupiedPalettesNo62[0] > 0) setClass("unoccupied", "Nije zauzeto", "#621");	
	$('#622').text(arrUnoccupiedPalettesNo62[1]);
	//if(arrUnoccupiedPalettesNo62[1] === 0) {setClass("occupied", "Zauzeto", "#622");}
	//if(arrUnoccupiedPalettesNo62[1] > 0) setClass("unoccupied", "Nije zauzeto", "#622");
	$('#623').text(arrUnoccupiedPalettesNo62[2]);
	//if(arrUnoccupiedPalettesNo62[2] === 0) setClass("occupied", "Zauzeto", "#623");
	//if(arrUnoccupiedPalettesNo62[2] > 0) setClass("unoccupied", "Nije zauzeto", "#623");
	$('#624').text(arrUnoccupiedPalettesNo62[3]);
	//if(arrUnoccupiedPalettesNo62[3] === 0) setClass("occupied", "Zauzeto", "#624");
	//if(arrUnoccupiedPalettesNo62[3] > 0) setClass("unoccupied", "Nije zauzeto", "#624");
	$('#625').text(arrUnoccupiedPalettesNo62[4]);
	//if(arrUnoccupiedPalettesNo62[4] === 0) setClass("occupied", "Zauzeto", "#625");
	//if(arrUnoccupiedPalettesNo62[4] > 0) setClass("unoccupied", "Nije zauzeto", "#625");
	$('#626').text(arrUnoccupiedPalettesNo62[5]);
	//if(arrUnoccupiedPalettesNo62[5] === 0) setClass("occupied", "Zauzeto", "#626");
	//if(arrUnoccupiedPalettesNo62[5] > 0) setClass("unoccupied", "Nije zauzeto", "#626");
	$('#627').text(arrUnoccupiedPalettesNo62[6]);
	//if(arrUnoccupiedPalettesNo62[6] === 0) setClass("occupied", "Zauzeto", "#627");
	//if(arrUnoccupiedPalettesNo62[6] > 0) setClass("unoccupied", "Nije zauzeto", "#627");	
	
	
	let arrTotalPalettesNo51 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo51 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo51 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes51.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo51[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo51[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo51[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo51[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo51[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo51[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo51[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo51[i] = arrTotalPalettesNo51[i] - arrOccupiedPalettesNo51[i];
		
	}		

	$('#511').text(arrUnoccupiedPalettesNo51[0]);
	//if(arrUnoccupiedPalettesNo51[0] === 0) setClass("occupied", "Zauzeto", "#511");	
	//if(arrUnoccupiedPalettesNo51[0] > 0) setClass("unoccupied", "Nije zauzeto", "#511");	
	$('#512').text(arrUnoccupiedPalettesNo51[1]);
	//if(arrUnoccupiedPalettesNo51[1] === 0) {setClass("occupied", "Zauzeto", "#512");}
	//if(arrUnoccupiedPalettesNo51[1] > 0) setClass("unoccupied", "Nije zauzeto", "#512");
	$('#513').text(arrUnoccupiedPalettesNo51[2]);
	//if(arrUnoccupiedPalettesNo51[2] === 0) setClass("occupied", "Zauzeto", "#513");
	//if(arrUnoccupiedPalettesNo51[2] > 0) setClass("unoccupied", "Nije zauzeto", "#513");
	$('#514').text(arrUnoccupiedPalettesNo51[3]);
	//if(arrUnoccupiedPalettesNo51[3] === 0) setClass("occupied", "Zauzeto", "#514");
	//if(arrUnoccupiedPalettesNo51[3] > 0) setClass("unoccupied", "Nije zauzeto", "#514");
	$('#515').text(arrUnoccupiedPalettesNo51[4]);
	//if(arrUnoccupiedPalettesNo51[4] === 0) setClass("occupied", "Zauzeto", "#515");
	//if(arrUnoccupiedPalettesNo51[4] > 0) setClass("unoccupied", "Nije zauzeto", "#515");
	$('#516').text(arrUnoccupiedPalettesNo51[5]);
	//if(arrUnoccupiedPalettesNo51[5] === 0) setClass("occupied", "Zauzeto", "#516");
	//if(arrUnoccupiedPalettesNo51[5] > 0) setClass("unoccupied", "Nije zauzeto", "#516");
	$('#517').text(arrUnoccupiedPalettesNo51[6]);
	//if(arrUnoccupiedPalettesNo51[6] === 0) setClass("occupied", "Zauzeto", "#517");
	//if(arrUnoccupiedPalettesNo51[6] > 0) setClass("unoccupied", "Nije zauzeto", "#517");	
	
	
	let arrTotalPalettesNo52 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo52 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo52 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes52.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo52[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo52[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo52[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo52[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo52[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo52[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo52[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo52[i] = arrTotalPalettesNo52[i] - arrOccupiedPalettesNo52[i];
		
	}		

	$('#521').text(arrUnoccupiedPalettesNo52[0]);
	//if(arrUnoccupiedPalettesNo52[0] === 0) setClass("occupied", "Zauzeto", "#521");	
	//if(arrUnoccupiedPalettesNo52[0] > 0) setClass("unoccupied", "Nije zauzeto", "#521");	
	$('#522').text(arrUnoccupiedPalettesNo52[1]);
	//if(arrUnoccupiedPalettesNo52[1] === 0) {setClass("occupied", "Zauzeto", "#522");}
	//if(arrUnoccupiedPalettesNo52[1] > 0) setClass("unoccupied", "Nije zauzeto", "#522");
	$('#523').text(arrUnoccupiedPalettesNo52[2]);
	//if(arrUnoccupiedPalettesNo52[2] === 0) setClass("occupied", "Zauzeto", "#523");
	//if(arrUnoccupiedPalettesNo52[2] > 0) setClass("unoccupied", "Nije zauzeto", "#523");
	$('#524').text(arrUnoccupiedPalettesNo52[3]);
	//if(arrUnoccupiedPalettesNo52[3] === 0) setClass("occupied", "Zauzeto", "#524");
	//if(arrUnoccupiedPalettesNo52[3] > 0) setClass("unoccupied", "Nije zauzeto", "#524");
	$('#525').text(arrUnoccupiedPalettesNo52[4]);
	//if(arrUnoccupiedPalettesNo52[4] === 0) setClass("occupied", "Zauzeto", "#525");
	//if(arrUnoccupiedPalettesNo52[4] > 0) setClass("unoccupied", "Nije zauzeto", "#525");
	$('#526').text(arrUnoccupiedPalettesNo52[5]);
	//if(arrUnoccupiedPalettesNo52[5] === 0) setClass("occupied", "Zauzeto", "#526");
	//if(arrUnoccupiedPalettesNo52[5] > 0) setClass("unoccupied", "Nije zauzeto", "#526");
	$('#527').text(arrUnoccupiedPalettesNo52[6]);
	//if(arrUnoccupiedPalettesNo52[6] === 0) setClass("occupied", "Zauzeto", "#527");
	//if(arrUnoccupiedPalettesNo52[6] > 0) setClass("unoccupied", "Nije zauzeto", "#527");	
	
	
	let arrTotalPalettesNo41 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo41 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo41 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes41.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo41[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo41[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo41[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo41[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo41[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo41[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo41[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo41[i] = arrTotalPalettesNo41[i] - arrOccupiedPalettesNo41[i];
		
	}		

	$('#411').text(arrUnoccupiedPalettesNo41[0]);
	//if(arrUnoccupiedPalettesNo41[0] === 0) setClass("occupied", "Zauzeto", "#411");	
	//if(arrUnoccupiedPalettesNo41[0] > 0) setClass("unoccupied", "Nije zauzeto", "#411");	
	$('#412').text(arrUnoccupiedPalettesNo41[1]);
	//if(arrUnoccupiedPalettesNo41[1] === 0) {setClass("occupied", "Zauzeto", "#412");}
	//if(arrUnoccupiedPalettesNo41[1] > 0) setClass("unoccupied", "Nije zauzeto", "#412");
	$('#413').text(arrUnoccupiedPalettesNo41[2]);
	//if(arrUnoccupiedPalettesNo41[2] === 0) setClass("occupied", "Zauzeto", "#413");
	//if(arrUnoccupiedPalettesNo41[2] > 0) setClass("unoccupied", "Nije zauzeto", "#413");
	$('#414').text(arrUnoccupiedPalettesNo41[3]);
	//if(arrUnoccupiedPalettesNo41[3] === 0) setClass("occupied", "Zauzeto", "#414");
	//if(arrUnoccupiedPalettesNo41[3] > 0) setClass("unoccupied", "Nije zauzeto", "#414");
	$('#415').text(arrUnoccupiedPalettesNo41[4]);
	//if(arrUnoccupiedPalettesNo41[4] === 0) setClass("occupied", "Zauzeto", "#415");
	//if(arrUnoccupiedPalettesNo41[4] > 0) setClass("unoccupied", "Nije zauzeto", "#415");
	$('#416').text(arrUnoccupiedPalettesNo41[5]);
	//if(arrUnoccupiedPalettesNo41[5] === 0) setClass("occupied", "Zauzeto", "#416");
	//if(arrUnoccupiedPalettesNo41[5] > 0) setClass("unoccupied", "Nije zauzeto", "#416");
	$('#417').text(arrUnoccupiedPalettesNo41[6]);
	//if(arrUnoccupiedPalettesNo41[6] === 0) setClass("occupied", "Zauzeto", "#417");
	//if(arrUnoccupiedPalettesNo41[6] > 0) setClass("unoccupied", "Nije zauzeto", "#417");	
	
	
	let arrTotalPalettesNo42 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo42 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo42 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes42.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo42[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo42[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo42[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo42[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo42[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo42[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo42[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo42[i] = arrTotalPalettesNo42[i] - arrOccupiedPalettesNo42[i];
		
	}		

	$('#421').text(arrUnoccupiedPalettesNo42[0]);
	//if(arrUnoccupiedPalettesNo42[0] === 0) setClass("occupied", "Zauzeto", "#421");	
	//if(arrUnoccupiedPalettesNo42[0] > 0) setClass("unoccupied", "Nije zauzeto", "#421");	
	$('#422').text(arrUnoccupiedPalettesNo42[1]);
	//if(arrUnoccupiedPalettesNo42[1] === 0) {setClass("occupied", "Zauzeto", "#422");}
	//if(arrUnoccupiedPalettesNo42[1] > 0) setClass("unoccupied", "Nije zauzeto", "#422");
	$('#423').text(arrUnoccupiedPalettesNo42[2]);
	//if(arrUnoccupiedPalettesNo42[2] === 0) setClass("occupied", "Zauzeto", "#423");
	//if(arrUnoccupiedPalettesNo42[2] > 0) setClass("unoccupied", "Nije zauzeto", "#423");
	$('#424').text(arrUnoccupiedPalettesNo42[3]);
	//if(arrUnoccupiedPalettesNo42[3] === 0) setClass("occupied", "Zauzeto", "#424");
	//if(arrUnoccupiedPalettesNo42[3] > 0) setClass("unoccupied", "Nije zauzeto", "#424");
	$('#425').text(arrUnoccupiedPalettesNo42[4]);
	//if(arrUnoccupiedPalettesNo42[4] === 0) setClass("occupied", "Zauzeto", "#425");
	//if(arrUnoccupiedPalettesNo42[4] > 0) setClass("unoccupied", "Nije zauzeto", "#425");
	$('#426').text(arrUnoccupiedPalettesNo42[5]);
	//if(arrUnoccupiedPalettesNo42[5] === 0) setClass("occupied", "Zauzeto", "#426");
	//if(arrUnoccupiedPalettesNo42[5] > 0) setClass("unoccupied", "Nije zauzeto", "#426");
	$('#427').text(arrUnoccupiedPalettesNo42[6]);
	//if(arrUnoccupiedPalettesNo42[6] === 0) setClass("occupied", "Zauzeto", "#427");
	//if(arrUnoccupiedPalettesNo42[6] > 0) setClass("unoccupied", "Nije zauzeto", "#427");	
	
	
	let arrTotalPalettesNo31 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo31 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo31 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes31.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo31[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo31[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo31[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo31[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo31[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo31[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo31[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo31[i] = arrTotalPalettesNo31[i] - arrOccupiedPalettesNo31[i];
		
	}		

	$('#311').text(arrUnoccupiedPalettesNo31[0]);
	//if(arrUnoccupiedPalettesNo31[0] === 0) setClass("occupied", "Zauzeto", "#311");	
	//if(arrUnoccupiedPalettesNo31[0] > 0) setClass("unoccupied", "Nije zauzeto", "#311");	
	$('#312').text(arrUnoccupiedPalettesNo31[1]);
	//if(arrUnoccupiedPalettesNo31[1] === 0) {setClass("occupied", "Zauzeto", "#312");}
	//if(arrUnoccupiedPalettesNo31[1] > 0) setClass("unoccupied", "Nije zauzeto", "#312");
	$('#313').text(arrUnoccupiedPalettesNo31[2]);
	//if(arrUnoccupiedPalettesNo31[2] === 0) setClass("occupied", "Zauzeto", "#313");
	//if(arrUnoccupiedPalettesNo31[2] > 0) setClass("unoccupied", "Nije zauzeto", "#313");
	$('#314').text(arrUnoccupiedPalettesNo31[3]);
	//if(arrUnoccupiedPalettesNo31[3] === 0) setClass("occupied", "Zauzeto", "#314");
	//if(arrUnoccupiedPalettesNo31[3] > 0) setClass("unoccupied", "Nije zauzeto", "#314");
	$('#315').text(arrUnoccupiedPalettesNo31[4]);
	//if(arrUnoccupiedPalettesNo31[4] === 0) setClass("occupied", "Zauzeto", "#315");
	//if(arrUnoccupiedPalettesNo31[4] > 0) setClass("unoccupied", "Nije zauzeto", "#315");
	$('#316').text(arrUnoccupiedPalettesNo31[5]);
	//if(arrUnoccupiedPalettesNo31[5] === 0) setClass("occupied", "Zauzeto", "#316");
	//if(arrUnoccupiedPalettesNo31[5] > 0) setClass("unoccupied", "Nije zauzeto", "#316");
	$('#317').text(arrUnoccupiedPalettesNo31[6]);
	//if(arrUnoccupiedPalettesNo31[6] === 0) setClass("occupied", "Zauzeto", "#317");
	//if(arrUnoccupiedPalettesNo31[6] > 0) setClass("unoccupied", "Nije zauzeto", "#317");	
	
	
	let arrTotalPalettesNo32 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo32 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo32 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes32.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo32[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo32[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo32[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo32[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo32[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo32[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo32[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo32[i] = arrTotalPalettesNo32[i] - arrOccupiedPalettesNo32[i];
		
	}		

	$('#321').text(arrUnoccupiedPalettesNo32[0]);
	//if(arrUnoccupiedPalettesNo32[0] === 0) setClass("occupied", "Zauzeto", "#321");	
	//if(arrUnoccupiedPalettesNo32[0] > 0) setClass("unoccupied", "Nije zauzeto", "#321");	
	$('#322').text(arrUnoccupiedPalettesNo32[1]);
	//if(arrUnoccupiedPalettesNo32[1] === 0) {setClass("occupied", "Zauzeto", "#322");}
	//if(arrUnoccupiedPalettesNo32[1] > 0) setClass("unoccupied", "Nije zauzeto", "#322");
	$('#323').text(arrUnoccupiedPalettesNo32[2]);
	//if(arrUnoccupiedPalettesNo32[2] === 0) setClass("occupied", "Zauzeto", "#323");
	//if(arrUnoccupiedPalettesNo32[2] > 0) setClass("unoccupied", "Nije zauzeto", "#323");
	$('#324').text(arrUnoccupiedPalettesNo32[3]);
	//if(arrUnoccupiedPalettesNo32[3] === 0) setClass("occupied", "Zauzeto", "#324");
	//if(arrUnoccupiedPalettesNo32[3] > 0) setClass("unoccupied", "Nije zauzeto", "#324");
	$('#325').text(arrUnoccupiedPalettesNo32[4]);
	//if(arrUnoccupiedPalettesNo32[4] === 0) setClass("occupied", "Zauzeto", "#325");
	//if(arrUnoccupiedPalettesNo32[4] > 0) setClass("unoccupied", "Nije zauzeto", "#325");
	$('#326').text(arrUnoccupiedPalettesNo32[5]);
	//if(arrUnoccupiedPalettesNo32[5] === 0) setClass("occupied", "Zauzeto", "#326");
	//if(arrUnoccupiedPalettesNo32[5] > 0) setClass("unoccupied", "Nije zauzeto", "#326");
	$('#327').text(arrUnoccupiedPalettesNo32[6]);
	//if(arrUnoccupiedPalettesNo32[6] === 0) setClass("occupied", "Zauzeto", "#327");
	//if(arrUnoccupiedPalettesNo32[6] > 0) setClass("unoccupied", "Nije zauzeto", "#327");	
	
	
	let arrTotalPalettesNo21 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo21 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo21 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes21.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo21[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo21[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo21[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo21[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo21[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo21[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo21[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo21[i] = arrTotalPalettesNo21[i] - arrOccupiedPalettesNo21[i];
		
	}		

	$('#211').text(arrUnoccupiedPalettesNo21[0]);
	//if(arrUnoccupiedPalettesNo21[0] === 0) setClass("occupied", "Zauzeto", "#211");	
	//if(arrUnoccupiedPalettesNo21[0] > 0) setClass("unoccupied", "Nije zauzeto", "#211");	
	$('#212').text(arrUnoccupiedPalettesNo21[1]);
	//if(arrUnoccupiedPalettesNo21[1] === 0) {setClass("occupied", "Zauzeto", "#212");}
	//if(arrUnoccupiedPalettesNo21[1] > 0) setClass("unoccupied", "Nije zauzeto", "#212");
	$('#213').text(arrUnoccupiedPalettesNo21[2]);
	//if(arrUnoccupiedPalettesNo21[2] === 0) setClass("occupied", "Zauzeto", "#213");
	//if(arrUnoccupiedPalettesNo21[2] > 0) setClass("unoccupied", "Nije zauzeto", "#213");
	$('#214').text(arrUnoccupiedPalettesNo21[3]);
	//if(arrUnoccupiedPalettesNo21[3] === 0) setClass("occupied", "Zauzeto", "#214");
	//if(arrUnoccupiedPalettesNo21[3] > 0) setClass("unoccupied", "Nije zauzeto", "#214");
	$('#215').text(arrUnoccupiedPalettesNo21[4]);
	//if(arrUnoccupiedPalettesNo21[4] === 0) setClass("occupied", "Zauzeto", "#215");
	//if(arrUnoccupiedPalettesNo21[4] > 0) setClass("unoccupied", "Nije zauzeto", "#215");
	$('#216').text(arrUnoccupiedPalettesNo21[5]);
	//if(arrUnoccupiedPalettesNo21[5] === 0) setClass("occupied", "Zauzeto", "#216");
	//if(arrUnoccupiedPalettesNo21[5] > 0) setClass("unoccupied", "Nije zauzeto", "#216");
	$('#217').text(arrUnoccupiedPalettesNo21[6]);
	//if(arrUnoccupiedPalettesNo21[6] === 0) setClass("occupied", "Zauzeto", "#217");
	//if(arrUnoccupiedPalettesNo21[6] > 0) setClass("unoccupied", "Nije zauzeto", "#217");	
	
	
	let arrTotalPalettesNo22 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo22 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo22 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes22.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo22[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo22[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo22[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo22[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo22[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo22[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo22[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo22[i] = arrTotalPalettesNo22[i] - arrOccupiedPalettesNo22[i];
		
	}		

	$('#221').text(arrUnoccupiedPalettesNo22[0]);
	//if(arrUnoccupiedPalettesNo22[0] === 0) setClass("occupied", "Zauzeto", "#221");	
	//if(arrUnoccupiedPalettesNo22[0] > 0) setClass("unoccupied", "Nije zauzeto", "#221");	
	$('#222').text(arrUnoccupiedPalettesNo22[1]);
	//if(arrUnoccupiedPalettesNo22[1] === 0) {setClass("occupied", "Zauzeto", "#222");}
	//if(arrUnoccupiedPalettesNo22[1] > 0) setClass("unoccupied", "Nije zauzeto", "#222");
	$('#223').text(arrUnoccupiedPalettesNo22[2]);
	//if(arrUnoccupiedPalettesNo22[2] === 0) setClass("occupied", "Zauzeto", "#223");
	//if(arrUnoccupiedPalettesNo22[2] > 0) setClass("unoccupied", "Nije zauzeto", "#223");
	$('#224').text(arrUnoccupiedPalettesNo22[3]);
	//if(arrUnoccupiedPalettesNo22[3] === 0) setClass("occupied", "Zauzeto", "#224");
	//if(arrUnoccupiedPalettesNo22[3] > 0) setClass("unoccupied", "Nije zauzeto", "#224");
	$('#225').text(arrUnoccupiedPalettesNo22[4]);
	//if(arrUnoccupiedPalettesNo22[4] === 0) setClass("occupied", "Zauzeto", "#225");
	//if(arrUnoccupiedPalettesNo22[4] > 0) setClass("unoccupied", "Nije zauzeto", "#225");
	$('#226').text(arrUnoccupiedPalettesNo22[5]);
	//if(arrUnoccupiedPalettesNo22[5] === 0) setClass("occupied", "Zauzeto", "#226");
	//if(arrUnoccupiedPalettesNo22[5] > 0) setClass("unoccupied", "Nije zauzeto", "#226");
	$('#227').text(arrUnoccupiedPalettesNo22[6]);
	//if(arrUnoccupiedPalettesNo22[6] === 0) setClass("occupied", "Zauzeto", "#227");
	//if(arrUnoccupiedPalettesNo22[6] > 0) setClass("unoccupied", "Nije zauzeto", "#227");	
	
	
	let arrTotalPalettesNo11 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo11 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo11 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes11.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo11[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo11[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo11[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo11[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo11[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo11[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo11[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo11[i] = arrTotalPalettesNo11[i] - arrOccupiedPalettesNo11[i];
		
	}		

	$('#111').text(arrUnoccupiedPalettesNo11[0]);
	//if(arrUnoccupiedPalettesNo11[0] === 0) setClass("occupied", "Zauzeto", "#111");	
	//if(arrUnoccupiedPalettesNo11[0] > 0) setClass("unoccupied", "Nije zauzeto", "#111");	
	$('#112').text(arrUnoccupiedPalettesNo11[1]);
	//if(arrUnoccupiedPalettesNo11[1] === 0) {setClass("occupied", "Zauzeto", "#112");}
	//if(arrUnoccupiedPalettesNo11[1] > 0) setClass("unoccupied", "Nije zauzeto", "#112");
	$('#113').text(arrUnoccupiedPalettesNo11[2]);
	//if(arrUnoccupiedPalettesNo11[2] === 0) setClass("occupied", "Zauzeto", "#113");
	//if(arrUnoccupiedPalettesNo11[2] > 0) setClass("unoccupied", "Nije zauzeto", "#113");
	$('#114').text(arrUnoccupiedPalettesNo11[3]);
	//if(arrUnoccupiedPalettesNo11[3] === 0) setClass("occupied", "Zauzeto", "#114");
	//if(arrUnoccupiedPalettesNo11[3] > 0) setClass("unoccupied", "Nije zauzeto", "#114");
	$('#115').text(arrUnoccupiedPalettesNo11[4]);
	//if(arrUnoccupiedPalettesNo11[4] === 0) setClass("occupied", "Zauzeto", "#115");
	//if(arrUnoccupiedPalettesNo11[4] > 0) setClass("unoccupied", "Nije zauzeto", "#115");
	$('#116').text(arrUnoccupiedPalettesNo11[5]);
	//if(arrUnoccupiedPalettesNo11[5] === 0) setClass("occupied", "Zauzeto", "#116");
	//if(arrUnoccupiedPalettesNo11[5] > 0) setClass("unoccupied", "Nije zauzeto", "#116");
	$('#117').text(arrUnoccupiedPalettesNo11[6]);
	//if(arrUnoccupiedPalettesNo11[6] === 0) setClass("occupied", "Zauzeto", "#117");
	//if(arrUnoccupiedPalettesNo11[6] > 0) setClass("unoccupied", "Nije zauzeto", "#117");	
	
	
	let arrTotalPalettesNo12 = [102, 102, 102, 102, 102, 102, 102];
	let arrOccupiedPalettesNo12 = [0, 0, 0, 0, 0, 0, 0];
	let arrUnoccupiedPalettesNo12 = [];

	// set occupied palettes		
	arrOccupiedPaletteCodes12.forEach (e => {
		
		switch (e.paletteCode.substring(5, 7)) {
			case '01':
				arrOccupiedPalettesNo12[0]++;
			break;
			case '02':
	            arrOccupiedPalettesNo12[1]++;
			break;
			case '03':
	            arrOccupiedPalettesNo12[2]++;
			break;
			case '04':
	            arrOccupiedPalettesNo12[3]++;
			break;
			case '05':
	            arrOccupiedPalettesNo12[4]++;
			break;
			case '06':
	            arrOccupiedPalettesNo12[5]++;
			break;
			case '07':
	            arrOccupiedPalettesNo12[6]++;
			break;
		}

	});

	for (let i = 0; i < 7; i++) {
		arrUnoccupiedPalettesNo12[i] = arrTotalPalettesNo12[i] - arrOccupiedPalettesNo12[i];
		
	}		

	$('#121').text(arrUnoccupiedPalettesNo12[0]);
	//if(arrUnoccupiedPalettesNo12[0] === 0) setClass("occupied", "Zauzeto", "#121");	
	//if(arrUnoccupiedPalettesNo12[0] > 0) setClass("unoccupied", "Nije zauzeto", "#121");	
	$('#122').text(arrUnoccupiedPalettesNo12[1]);
	//if(arrUnoccupiedPalettesNo12[1] === 0) {setClass("occupied", "Zauzeto", "#122");}
	//if(arrUnoccupiedPalettesNo12[1] > 0) setClass("unoccupied", "Nije zauzeto", "#122");
	$('#123').text(arrUnoccupiedPalettesNo12[2]);
	//if(arrUnoccupiedPalettesNo12[2] === 0) setClass("occupied", "Zauzeto", "#123");
	//if(arrUnoccupiedPalettesNo12[2] > 0) setClass("unoccupied", "Nije zauzeto", "#123");
	$('#124').text(arrUnoccupiedPalettesNo12[3]);
	//if(arrUnoccupiedPalettesNo12[3] === 0) setClass("occupied", "Zauzeto", "#124");
	//if(arrUnoccupiedPalettesNo12[3] > 0) setClass("unoccupied", "Nije zauzeto", "#124");
	$('#125').text(arrUnoccupiedPalettesNo12[4]);
	//if(arrUnoccupiedPalettesNo12[4] === 0) setClass("occupied", "Zauzeto", "#125");
	//if(arrUnoccupiedPalettesNo12[4] > 0) setClass("unoccupied", "Nije zauzeto", "#125");
	$('#126').text(arrUnoccupiedPalettesNo12[5]);
	//if(arrUnoccupiedPalettesNo12[5] === 0) setClass("occupied", "Zauzeto", "#126");
	//if(arrUnoccupiedPalettesNo12[5] > 0) setClass("unoccupied", "Nije zauzeto", "#126");
	$('#127').text(arrUnoccupiedPalettesNo12[6]);
	//if(arrUnoccupiedPalettesNo12[6] === 0) setClass("occupied", "Zauzeto", "#127");
	//if(arrUnoccupiedPalettesNo12[6] > 0) setClass("unoccupied", "Nije zauzeto", "#127");
	
	
	const sourceField = document.getElementById('palletesNo');
	// Select all 100 target fields using a common class name
	const targetFields = document.querySelectorAll('.color-target');

	sourceField.addEventListener('input', () => {
	    const newValue = sourceField.value;

	    // Loop through all 100 elements instantly
	    targetFields.forEach(field => {
			if(newValue !== "" && parseInt(newValue.trim()) === parseInt(field.textContent.trim())) {
	            field.style.backgroundColor = "#2ecc71";
			} else if(newValue !== "" && parseInt(newValue.trim()) > parseInt(field.textContent.trim())) {
			    field.style.backgroundColor = "#e65565";
			} else if(newValue !== "" && parseInt(newValue.trim()) < parseInt(field.textContent.trim())) {
			    field.style.backgroundColor = "#fdd49e";
			} else {
				field.style.backgroundColor = "";
			}				
	    });
	});	
	
	
	const avr = arrUnoccupiedPalettesNumber => arrUnoccupiedPalettesNumber.length ? arrUnoccupiedPalettesNumber.reduce((a, b) => a + b) / arrUnoccupiedPalettesNumber.length : 0;
    const index = average(arrUnoccupiedPalettesNumber, avr(arrUnoccupiedPalettesNumber));	
	for(let i=0; i<16; i++) {		
		switch (i) {
			case 0:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#81');
				} else if(arrUnoccupiedPalettesNumber[0] === 0) {
					setClass("occupied", "Zauzeto", "#81");					
				} else if(arrUnoccupiedPalettesNumber[0] > 0) {
				    setClass("processing", "Nije zauzeto", "#81");					
				}
			break;
			case 1:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#82');
				} else if(arrUnoccupiedPalettesNumber[1] === 0) {
					setClass("occupied", "Zauzeto", "#82");					
				} else if(arrUnoccupiedPalettesNumber[1] > 0) {
				    setClass("processing", "Nije zauzeto", "#82");					
				}
			break;
			case 2:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#71');
				} else if(arrUnoccupiedPalettesNumber[2] === 0) {
					setClass("occupied", "Zauzeto", "#71");					
				} else if(arrUnoccupiedPalettesNumber[2] > 0) {
				    setClass("processing", "Nije zauzeto", "#71");					
				}
			break;
			case 3:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#72');
				} else if(arrUnoccupiedPalettesNumber[3] === 0) {
					setClass("occupied", "Zauzeto", "#72");					
				} else if(arrUnoccupiedPalettesNumber[3] > 0) {
				    setClass("processing", "Nije zauzeto", "#72");					
				}
			break;
			case 4:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#61');
				} else if(arrUnoccupiedPalettesNumber[4] === 0) {
					setClass("occupied", "Zauzeto", "#61");					
				} else if(arrUnoccupiedPalettesNumber[4] > 0) {
				    setClass("processing", "Nije zauzeto", "#61");					
				}
			break;
			case 5:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#62');
				} else if(arrUnoccupiedPalettesNumber[5] === 0) {
					setClass("occupied", "Zauzeto", "#62");					
				} else if(arrUnoccupiedPalettesNumber[5] > 0) {
				    setClass("processing", "Nije zauzeto", "#62");					
				}
			break;
			case 6:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#51');
				} else if(arrUnoccupiedPalettesNumber[6] === 0) {
					setClass("occupied", "Zauzeto", "#51");					
				} else if(arrUnoccupiedPalettesNumber[6] > 0) {
				    setClass("processing", "Nije zauzeto", "#51");					
				}
			break;
			case 7:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#52');
				} else if(arrUnoccupiedPalettesNumber[7] === 0) {
					setClass("occupied", "Zauzeto", "#52");					
				} else if(arrUnoccupiedPalettesNumber[7] > 0) {
				    setClass("processing", "Nije zauzeto", "#52");					
				}
			break;
			case 8:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#41');
				} else if(arrUnoccupiedPalettesNumber[8] === 0) {
					setClass("occupied", "Zauzeto", "#41");					
				} else if(arrUnoccupiedPalettesNumber[8] > 0) {
				    setClass("processing", "Nije zauzeto", "#41");					
				}
			break;
			case 9:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#42');
				} else if(arrUnoccupiedPalettesNumber[9] === 0) {
					setClass("occupied", "Zauzeto", "#42");					
				} else if(arrUnoccupiedPalettesNumber[9] > 0) {
				    setClass("processing", "Nije zauzeto", "#42");					
				}
			break;
			case 10:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '31');
				} else if(arrUnoccupiedPalettesNumber[10] === 0) {
					setClass("occupied", "Zauzeto", "#31");					
				} else if(arrUnoccupiedPalettesNumber[10] > 0) {
				    setClass("processing", "Nije zauzeto", "#31");					
				}
			break;
			case 11:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#32');
				} else if(arrUnoccupiedPalettesNumber[11] === 0) {
					setClass("occupied", "Zauzeto", "#32");					
				} else if(arrUnoccupiedPalettesNumber[11] > 0) {
				    setClass("processing", "Nije zauzeto", "#32");					
				}
			break;
			case 12:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#21');
				} else if(arrUnoccupiedPalettesNumber[12] === 0) {
					setClass("occupied", "Zauzeto", "#21");					
				} else if(arrUnoccupiedPalettesNumber[12] > 0) {
				    setClass("processing", "Nije zauzeto", "#21");					
				}
			break;
			case 13:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#22');
				} else if(arrUnoccupiedPalettesNumber[13] === 0) {
					setClass("occupied", "Zauzeto", "#22");					
				} else if(arrUnoccupiedPalettesNumber[13] > 0) {
				    setClass("processing", "Nije zauzeto", "#22");					
				}
			break;
			case 14:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#11');
				} else if(arrUnoccupiedPalettesNumber[14] === 0) {
					setClass("occupied", "Zauzeto", "#11");					
				} else if(arrUnoccupiedPalettesNumber[14] > 0) {
				    setClass("processing", "Nije zauzeto", "#11");					
				}
			break;
			case 15:
				if(i === index) {
					setClass('unoccupied', 'Nije zauzeto', '#12');
				} else if(arrUnoccupiedPalettesNumber[15] === 0) {
					setClass("occupied", "Zauzeto", "#12");					
				} else if(arrUnoccupiedPalettesNumber[15] > 0) {
				    setClass("processing", "Nije zauzeto", "#12");					
				}
			break;							
		}
				
	}
	
	jQuery.ajaxSetup({async:true});	
	
	// 1. Hook directly into the browser's native global window event tracker
	window.addEventListener('hidden.bs.modal', function (event) {
	    
		jQuery.ajaxSetup({async:false});	
		let arrOccupiedPaletteCodes81 = [];	
		let arrOccupiedPaletteCodes82 = [];
		let arrOccupiedPaletteCodes71 = [];
		let arrOccupiedPaletteCodes72 = [];
		let arrOccupiedPaletteCodes61 = [];
		let arrOccupiedPaletteCodes62 = [];
		let arrOccupiedPaletteCodes51 = [];
		let arrOccupiedPaletteCodes52 = [];
		let arrOccupiedPaletteCodes41 = [];
		let arrOccupiedPaletteCodes42 = [];
		let arrOccupiedPaletteCodes31 = [];	
		let arrOccupiedPaletteCodes32 = [];
		let arrOccupiedPaletteCodes21 = [];
		let arrOccupiedPaletteCodes22 = [];
		let arrOccupiedPaletteCodes11 = [];
		let arrOccupiedPaletteCodes12 = [];

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "81", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes81.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "82", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes82.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "71", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes71.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "72", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes72.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "61", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes61.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "62", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes62.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "51", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes51.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "52", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes52.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "41", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes41.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "42", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes42.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "31", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes31.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "32", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes32.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "21", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes21.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "22", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes22.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "11", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes11.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});	

		$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + "12", function( data ) {
			for (i= 0; i < data.data.length; i++) {
				arrOccupiedPaletteCodes12.push(
					{
						"paletteCode" : data.data[i].paletteCode.trim(),
						"statusId"    : data.data[i].statusId,
						"clientId"    : data.data[i].paletteDocument.clientId,
						"clientName"  : data.data[i].paletteDocument.client.name,
						"length"      : data.data[i].length,
						"width"       : data.data[i].width,
						"height"      : data.data[i].height
					}
				);
			}
		});										

		let arrUnoccupiedPalettesNumber = [];	
		let arrOccupiedPalettesNumber = [];
			
		/**
		 * Gets fixed List<Integer> with 3 elements, where: 1st is totalCnt, 2nd is occupiedCnt, 3rd is freeCnt
		 */
		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "81", function( data ) {
			/*$('#totalNo').text(data.data[0]);*/
			arrOccupiedPalettesNumber[0] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[0] = data.data[2];
			$('#81').text(data.data[2]);
			//if(arrUnoccupiedPalettesNumber[0] === 0) {setClass("occupied", "Zauzeto", "#81");}
			//if(arrUnoccupiedPalettesNumber[0] > 0) setClass("unoccupied", "Nije zauzeto", "#81");		
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "82", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[1] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);		
			arrUnoccupiedPalettesNumber[1] = data.data[2];
			$('#82').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "71", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[2] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[2] = data.data[2];
			$('#71').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "72", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[3] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[3] = data.data[2];
			$('#72').text(data.data[2]);
		});		

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "61", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[4] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[4] = data.data[2];
			$('#61').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "62", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[5] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[5] = data.data[2];
			$('#62').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "51", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[6] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[6] = data.data[2];
			$('#51').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "52", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[7] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[7] = data.data[2];
			$('#52').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "41", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[8] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[8] = data.data[2];
			$('#41').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "42", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[9] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[9] = data.data[2];
			$('#42').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "31", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[10] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[10] = data.data[2];
			$('#31').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "32", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[11] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[11] = data.data[2];
			$('#32').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "21", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[12] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[12] = data.data[2];
			$('#21').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "22", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[13] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[13] = data.data[2];
			$('#22').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "11", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[14] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[14] = data.data[2];
			$('#11').text(data.data[2]);
		});	

		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + "12", function( data ) {
			/*$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);*/
			arrOccupiedPalettesNumber[15] = data.data[1]
			//$('#occupiedNo').text(data.data[1]);
			arrUnoccupiedPalettesNumber[15] = data.data[2];
			$('#12').text(data.data[2]);
		});	


		let occupiedNo = 0;	
		let unoccupiedNo = 0;

		for (let i = 0; i < 16; i++) {
			occupiedNo += arrOccupiedPalettesNumber[i];
			unoccupiedNo += arrUnoccupiedPalettesNumber[i];
		}					

		/*let totalNo = columnNumber * 21;
		let occupiedNo = arrOccupiedPaletteCodes.length;
		let unoccupiedNo = totalNo - occupiedNo;
		$('#totalNo').text(totalNo);*/
		$('#occupiedNo1').text(occupiedNo);
		$('#unoccupiedNo1').text(unoccupiedNo);

		let arrTotalPalettesNo81 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo81 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo81 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes81.forEach (e => {
			/*let styleClass = "unoccupied";
			let title = "Nije zauzeto'";
			let selectedEl = '#' + e.paletteCode;
			
			$(selectedEl).removeClass('unoccupied occupied processing locked');*/
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo81[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo81[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo81[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo81[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo81[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo81[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo81[6]++;
				break;
			}
			

			/*$(selectedEl).addClass(styleClass);
			$(selectedEl).attr('title', title);
			$(selectedEl).attr('data-status-id', e.statusId);
			$(selectedEl).attr('data-client-id', e.clientId);
			$(selectedEl).attr('data-client-name', e.clientName);
			$(selectedEl).attr('data-length', e.length);
			$(selectedEl).attr('data-width', e.width);
			$(selectedEl).attr('data-height', e.height);*/
		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo81[i] = arrTotalPalettesNo81[i] - arrOccupiedPalettesNo81[i];
			
		}		

		$('#811').text(arrUnoccupiedPalettesNo81[0]);
		//if(arrUnoccupiedPalettesNo81[0] === 0) setClass("occupied", "Zauzeto", "#811");	
		//if(arrUnoccupiedPalettesNo81[0] > 0) setClass("unoccupied", "Nije zauzeto", "#811");	
		$('#812').text(arrUnoccupiedPalettesNo81[1]);
		//if(arrUnoccupiedPalettesNo81[1] === 0) {setClass("occupied", "Zauzeto", "#812");}
		//if(arrUnoccupiedPalettesNo81[1] > 0) setClass("unoccupied", "Nije zauzeto", "#812");
		$('#813').text(arrUnoccupiedPalettesNo81[2]);
		//if(arrUnoccupiedPalettesNo81[2] === 0) setClass("occupied", "Zauzeto", "#813");
		//if(arrUnoccupiedPalettesNo81[2] > 0) setClass("unoccupied", "Nije zauzeto", "#813");
		$('#814').text(arrUnoccupiedPalettesNo81[3]);
		//if(arrUnoccupiedPalettesNo81[3] === 0) setClass("occupied", "Zauzeto", "#814");
		//if(arrUnoccupiedPalettesNo81[3] > 0) setClass("unoccupied", "Nije zauzeto", "#814");
		$('#815').text(arrUnoccupiedPalettesNo81[4]);
		//if(arrUnoccupiedPalettesNo81[4] === 0) setClass("occupied", "Zauzeto", "#815");
		//if(arrUnoccupiedPalettesNo81[4] > 0) setClass("unoccupied", "Nije zauzeto", "#815");
		$('#816').text(arrUnoccupiedPalettesNo81[5]);
		//if(arrUnoccupiedPalettesNo81[5] === 0) setClass("occupied", "Zauzeto", "#816");
		//if(arrUnoccupiedPalettesNo81[5] > 0) setClass("unoccupied", "Nije zauzeto", "#816");
		$('#817').text(arrUnoccupiedPalettesNo81[6]);
		//if(arrUnoccupiedPalettesNo81[6] === 0) setClass("occupied", "Zauzeto", "#817");
		//if(arrUnoccupiedPalettesNo81[6] > 0) setClass("unoccupied", "Nije zauzeto", "#817");


		let arrTotalPalettesNo82 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo82 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo82 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes82.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo82[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo82[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo82[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo82[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo82[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo82[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo82[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo82[i] = arrTotalPalettesNo82[i] - arrOccupiedPalettesNo82[i];
			
		}		

		$('#821').text(arrUnoccupiedPalettesNo82[0]);
		//if(arrUnoccupiedPalettesNo82[0] === 0) setClass("occupied", "Zauzeto", "#821");	
		//if(arrUnoccupiedPalettesNo82[0] > 0) setClass("unoccupied", "Nije zauzeto", "#821");	
		$('#822').text(arrUnoccupiedPalettesNo82[1]);
		//if(arrUnoccupiedPalettesNo82[1] === 0) {setClass("occupied", "Zauzeto", "#822");}
		//if(arrUnoccupiedPalettesNo82[1] > 0) setClass("unoccupied", "Nije zauzeto", "#822");
		$('#823').text(arrUnoccupiedPalettesNo82[2]);
		//if(arrUnoccupiedPalettesNo82[2] === 0) setClass("occupied", "Zauzeto", "#823");
		//if(arrUnoccupiedPalettesNo82[2] > 0) setClass("unoccupied", "Nije zauzeto", "#823");
		$('#824').text(arrUnoccupiedPalettesNo82[3]);
		//if(arrUnoccupiedPalettesNo82[3] === 0) setClass("occupied", "Zauzeto", "#824");
		//if(arrUnoccupiedPalettesNo82[3] > 0) setClass("unoccupied", "Nije zauzeto", "#824");
		$('#825').text(arrUnoccupiedPalettesNo82[4]);
		//if(arrUnoccupiedPalettesNo82[4] === 0) setClass("occupied", "Zauzeto", "#825");
		//if(arrUnoccupiedPalettesNo82[4] > 0) setClass("unoccupied", "Nije zauzeto", "#825");
		$('#826').text(arrUnoccupiedPalettesNo82[5]);
		//if(arrUnoccupiedPalettesNo82[5] === 0) setClass("occupied", "Zauzeto", "#826");
		//if(arrUnoccupiedPalettesNo82[5] > 0) setClass("unoccupied", "Nije zauzeto", "#826");
		$('#827').text(arrUnoccupiedPalettesNo82[6]);
		//if(arrUnoccupiedPalettesNo82[6] === 0) setClass("occupied", "Zauzeto", "#827");
		//if(arrUnoccupiedPalettesNo82[6] > 0) setClass("unoccupied", "Nije zauzeto", "#827");	


		let arrTotalPalettesNo71 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo71 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo71 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes71.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo71[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo71[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo71[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo71[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo71[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo71[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo71[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo71[i] = arrTotalPalettesNo71[i] - arrOccupiedPalettesNo71[i];
			
		}		

		$('#711').text(arrUnoccupiedPalettesNo71[0]);
		//if(arrUnoccupiedPalettesNo71[0] === 0) setClass("occupied", "Zauzeto", "#711");	
		//if(arrUnoccupiedPalettesNo71[0] > 0) setClass("unoccupied", "Nije zauzeto", "#711");	
		$('#712').text(arrUnoccupiedPalettesNo71[1]);
		//if(arrUnoccupiedPalettesNo71[1] === 0) {setClass("occupied", "Zauzeto", "#712");}
		//if(arrUnoccupiedPalettesNo71[1] > 0) setClass("unoccupied", "Nije zauzeto", "#712");
		$('#713').text(arrUnoccupiedPalettesNo71[2]);
		//if(arrUnoccupiedPalettesNo71[2] === 0) setClass("occupied", "Zauzeto", "#713");
		//if(arrUnoccupiedPalettesNo71[2] > 0) setClass("unoccupied", "Nije zauzeto", "#713");
		$('#714').text(arrUnoccupiedPalettesNo71[3]);
		//if(arrUnoccupiedPalettesNo71[3] === 0) setClass("occupied", "Zauzeto", "#714");
		//if(arrUnoccupiedPalettesNo71[3] > 0) setClass("unoccupied", "Nije zauzeto", "#714");
		$('#715').text(arrUnoccupiedPalettesNo71[4]);
		//if(arrUnoccupiedPalettesNo71[4] === 0) setClass("occupied", "Zauzeto", "#715");
		//if(arrUnoccupiedPalettesNo71[4] > 0) setClass("unoccupied", "Nije zauzeto", "#715");
		$('#716').text(arrUnoccupiedPalettesNo71[5]);
		//if(arrUnoccupiedPalettesNo71[5] === 0) setClass("occupied", "Zauzeto", "#716");
		//if(arrUnoccupiedPalettesNo71[5] > 0) setClass("unoccupied", "Nije zauzeto", "#716");
		$('#717').text(arrUnoccupiedPalettesNo71[6]);
		//if(arrUnoccupiedPalettesNo71[6] === 0) setClass("occupied", "Zauzeto", "#717");
		//if(arrUnoccupiedPalettesNo71[6] > 0) setClass("unoccupied", "Nije zauzeto", "#717");	


		let arrTotalPalettesNo72 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo72 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo72 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes72.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo72[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo72[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo72[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo72[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo72[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo72[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo72[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo72[i] = arrTotalPalettesNo72[i] - arrOccupiedPalettesNo72[i];
			
		}		

		$('#721').text(arrUnoccupiedPalettesNo72[0]);
		//if(arrUnoccupiedPalettesNo72[0] === 0) setClass("occupied", "Zauzeto", "#721");	
		//if(arrUnoccupiedPalettesNo72[0] > 0) setClass("unoccupied", "Nije zauzeto", "#721");	
		$('#722').text(arrUnoccupiedPalettesNo72[1]);
		//if(arrUnoccupiedPalettesNo72[1] === 0) {setClass("occupied", "Zauzeto", "#722");}
		//if(arrUnoccupiedPalettesNo72[1] > 0) setClass("unoccupied", "Nije zauzeto", "#722");
		$('#723').text(arrUnoccupiedPalettesNo72[2]);
		//if(arrUnoccupiedPalettesNo72[2] === 0) setClass("occupied", "Zauzeto", "#723");
		//if(arrUnoccupiedPalettesNo72[2] > 0) setClass("unoccupied", "Nije zauzeto", "#723");
		$('#724').text(arrUnoccupiedPalettesNo72[3]);
		//if(arrUnoccupiedPalettesNo72[3] === 0) setClass("occupied", "Zauzeto", "#724");
		//if(arrUnoccupiedPalettesNo72[3] > 0) setClass("unoccupied", "Nije zauzeto", "#724");
		$('#725').text(arrUnoccupiedPalettesNo72[4]);
		//if(arrUnoccupiedPalettesNo72[4] === 0) setClass("occupied", "Zauzeto", "#725");
		//if(arrUnoccupiedPalettesNo72[4] > 0) setClass("unoccupied", "Nije zauzeto", "#725");
		$('#726').text(arrUnoccupiedPalettesNo72[5]);
		//if(arrUnoccupiedPalettesNo72[5] === 0) setClass("occupied", "Zauzeto", "#726");
		//if(arrUnoccupiedPalettesNo72[5] > 0) setClass("unoccupied", "Nije zauzeto", "#726");
		$('#727').text(arrUnoccupiedPalettesNo72[6]);
		//if(arrUnoccupiedPalettesNo72[6] === 0) setClass("occupied", "Zauzeto", "#727");
		//if(arrUnoccupiedPalettesNo72[6] > 0) setClass("unoccupied", "Nije zauzeto", "#727");	


		let arrTotalPalettesNo61 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo61 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo61 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes61.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo61[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo61[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo61[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo61[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo61[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo61[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo61[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo61[i] = arrTotalPalettesNo61[i] - arrOccupiedPalettesNo61[i];
			
		}		

		$('#611').text(arrUnoccupiedPalettesNo61[0]);
		//if(arrUnoccupiedPalettesNo61[0] === 0) setClass("occupied", "Zauzeto", "#611");	
		//if(arrUnoccupiedPalettesNo61[0] > 0) setClass("unoccupied", "Nije zauzeto", "#611");	
		$('#612').text(arrUnoccupiedPalettesNo61[1]);
		//if(arrUnoccupiedPalettesNo61[1] === 0) {setClass("occupied", "Zauzeto", "#612");}
		//if(arrUnoccupiedPalettesNo61[1] > 0) setClass("unoccupied", "Nije zauzeto", "#612");
		$('#613').text(arrUnoccupiedPalettesNo61[2]);
		//if(arrUnoccupiedPalettesNo61[2] === 0) setClass("occupied", "Zauzeto", "#613");
		//if(arrUnoccupiedPalettesNo61[2] > 0) setClass("unoccupied", "Nije zauzeto", "#613");
		$('#614').text(arrUnoccupiedPalettesNo61[3]);
		//if(arrUnoccupiedPalettesNo61[3] === 0) setClass("occupied", "Zauzeto", "#614");
		//if(arrUnoccupiedPalettesNo61[3] > 0) setClass("unoccupied", "Nije zauzeto", "#614");
		$('#615').text(arrUnoccupiedPalettesNo61[4]);
		//if(arrUnoccupiedPalettesNo61[4] === 0) setClass("occupied", "Zauzeto", "#615");
		//if(arrUnoccupiedPalettesNo61[4] > 0) setClass("unoccupied", "Nije zauzeto", "#615");
		$('#616').text(arrUnoccupiedPalettesNo61[5]);
		//if(arrUnoccupiedPalettesNo61[5] === 0) setClass("occupied", "Zauzeto", "#616");
		//if(arrUnoccupiedPalettesNo61[5] > 0) setClass("unoccupied", "Nije zauzeto", "#616");
		$('#617').text(arrUnoccupiedPalettesNo61[6]);
		//if(arrUnoccupiedPalettesNo61[6] === 0) setClass("occupied", "Zauzeto", "#617");
		//if(arrUnoccupiedPalettesNo61[6] > 0) setClass("unoccupied", "Nije zauzeto", "#617");	


		let arrTotalPalettesNo62 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo62 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo62 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes62.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo62[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo62[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo62[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo62[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo62[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo62[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo62[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo62[i] = arrTotalPalettesNo62[i] - arrOccupiedPalettesNo62[i];
			
		}		

		$('#621').text(arrUnoccupiedPalettesNo62[0]);
		//if(arrUnoccupiedPalettesNo62[0] === 0) setClass("occupied", "Zauzeto", "#621");	
		//if(arrUnoccupiedPalettesNo62[0] > 0) setClass("unoccupied", "Nije zauzeto", "#621");	
		$('#622').text(arrUnoccupiedPalettesNo62[1]);
		//if(arrUnoccupiedPalettesNo62[1] === 0) {setClass("occupied", "Zauzeto", "#622");}
		//if(arrUnoccupiedPalettesNo62[1] > 0) setClass("unoccupied", "Nije zauzeto", "#622");
		$('#623').text(arrUnoccupiedPalettesNo62[2]);
		//if(arrUnoccupiedPalettesNo62[2] === 0) setClass("occupied", "Zauzeto", "#623");
		//if(arrUnoccupiedPalettesNo62[2] > 0) setClass("unoccupied", "Nije zauzeto", "#623");
		$('#624').text(arrUnoccupiedPalettesNo62[3]);
		//if(arrUnoccupiedPalettesNo62[3] === 0) setClass("occupied", "Zauzeto", "#624");
		//if(arrUnoccupiedPalettesNo62[3] > 0) setClass("unoccupied", "Nije zauzeto", "#624");
		$('#625').text(arrUnoccupiedPalettesNo62[4]);
		//if(arrUnoccupiedPalettesNo62[4] === 0) setClass("occupied", "Zauzeto", "#625");
		//if(arrUnoccupiedPalettesNo62[4] > 0) setClass("unoccupied", "Nije zauzeto", "#625");
		$('#626').text(arrUnoccupiedPalettesNo62[5]);
		//if(arrUnoccupiedPalettesNo62[5] === 0) setClass("occupied", "Zauzeto", "#626");
		//if(arrUnoccupiedPalettesNo62[5] > 0) setClass("unoccupied", "Nije zauzeto", "#626");
		$('#627').text(arrUnoccupiedPalettesNo62[6]);
		//if(arrUnoccupiedPalettesNo62[6] === 0) setClass("occupied", "Zauzeto", "#627");
		//if(arrUnoccupiedPalettesNo62[6] > 0) setClass("unoccupied", "Nije zauzeto", "#627");	


		let arrTotalPalettesNo51 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo51 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo51 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes51.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo51[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo51[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo51[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo51[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo51[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo51[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo51[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo51[i] = arrTotalPalettesNo51[i] - arrOccupiedPalettesNo51[i];
			
		}		

		$('#511').text(arrUnoccupiedPalettesNo51[0]);
		//if(arrUnoccupiedPalettesNo51[0] === 0) setClass("occupied", "Zauzeto", "#511");	
		//if(arrUnoccupiedPalettesNo51[0] > 0) setClass("unoccupied", "Nije zauzeto", "#511");	
		$('#512').text(arrUnoccupiedPalettesNo51[1]);
		//if(arrUnoccupiedPalettesNo51[1] === 0) {setClass("occupied", "Zauzeto", "#512");}
		//if(arrUnoccupiedPalettesNo51[1] > 0) setClass("unoccupied", "Nije zauzeto", "#512");
		$('#513').text(arrUnoccupiedPalettesNo51[2]);
		//if(arrUnoccupiedPalettesNo51[2] === 0) setClass("occupied", "Zauzeto", "#513");
		//if(arrUnoccupiedPalettesNo51[2] > 0) setClass("unoccupied", "Nije zauzeto", "#513");
		$('#514').text(arrUnoccupiedPalettesNo51[3]);
		//if(arrUnoccupiedPalettesNo51[3] === 0) setClass("occupied", "Zauzeto", "#514");
		//if(arrUnoccupiedPalettesNo51[3] > 0) setClass("unoccupied", "Nije zauzeto", "#514");
		$('#515').text(arrUnoccupiedPalettesNo51[4]);
		//if(arrUnoccupiedPalettesNo51[4] === 0) setClass("occupied", "Zauzeto", "#515");
		//if(arrUnoccupiedPalettesNo51[4] > 0) setClass("unoccupied", "Nije zauzeto", "#515");
		$('#516').text(arrUnoccupiedPalettesNo51[5]);
		//if(arrUnoccupiedPalettesNo51[5] === 0) setClass("occupied", "Zauzeto", "#516");
		//if(arrUnoccupiedPalettesNo51[5] > 0) setClass("unoccupied", "Nije zauzeto", "#516");
		$('#517').text(arrUnoccupiedPalettesNo51[6]);
		//if(arrUnoccupiedPalettesNo51[6] === 0) setClass("occupied", "Zauzeto", "#517");
		//if(arrUnoccupiedPalettesNo51[6] > 0) setClass("unoccupied", "Nije zauzeto", "#517");	


		let arrTotalPalettesNo52 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo52 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo52 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes52.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo52[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo52[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo52[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo52[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo52[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo52[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo52[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo52[i] = arrTotalPalettesNo52[i] - arrOccupiedPalettesNo52[i];
			
		}		

		$('#521').text(arrUnoccupiedPalettesNo52[0]);
		//if(arrUnoccupiedPalettesNo52[0] === 0) setClass("occupied", "Zauzeto", "#521");	
		//if(arrUnoccupiedPalettesNo52[0] > 0) setClass("unoccupied", "Nije zauzeto", "#521");	
		$('#522').text(arrUnoccupiedPalettesNo52[1]);
		//if(arrUnoccupiedPalettesNo52[1] === 0) {setClass("occupied", "Zauzeto", "#522");}
		//if(arrUnoccupiedPalettesNo52[1] > 0) setClass("unoccupied", "Nije zauzeto", "#522");
		$('#523').text(arrUnoccupiedPalettesNo52[2]);
		//if(arrUnoccupiedPalettesNo52[2] === 0) setClass("occupied", "Zauzeto", "#523");
		//if(arrUnoccupiedPalettesNo52[2] > 0) setClass("unoccupied", "Nije zauzeto", "#523");
		$('#524').text(arrUnoccupiedPalettesNo52[3]);
		//if(arrUnoccupiedPalettesNo52[3] === 0) setClass("occupied", "Zauzeto", "#524");
		//if(arrUnoccupiedPalettesNo52[3] > 0) setClass("unoccupied", "Nije zauzeto", "#524");
		$('#525').text(arrUnoccupiedPalettesNo52[4]);
		//if(arrUnoccupiedPalettesNo52[4] === 0) setClass("occupied", "Zauzeto", "#525");
		//if(arrUnoccupiedPalettesNo52[4] > 0) setClass("unoccupied", "Nije zauzeto", "#525");
		$('#526').text(arrUnoccupiedPalettesNo52[5]);
		//if(arrUnoccupiedPalettesNo52[5] === 0) setClass("occupied", "Zauzeto", "#526");
		//if(arrUnoccupiedPalettesNo52[5] > 0) setClass("unoccupied", "Nije zauzeto", "#526");
		$('#527').text(arrUnoccupiedPalettesNo52[6]);
		//if(arrUnoccupiedPalettesNo52[6] === 0) setClass("occupied", "Zauzeto", "#527");
		//if(arrUnoccupiedPalettesNo52[6] > 0) setClass("unoccupied", "Nije zauzeto", "#527");	


		let arrTotalPalettesNo41 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo41 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo41 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes41.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo41[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo41[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo41[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo41[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo41[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo41[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo41[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo41[i] = arrTotalPalettesNo41[i] - arrOccupiedPalettesNo41[i];
			
		}		

		$('#411').text(arrUnoccupiedPalettesNo41[0]);
		//if(arrUnoccupiedPalettesNo41[0] === 0) setClass("occupied", "Zauzeto", "#411");	
		//if(arrUnoccupiedPalettesNo41[0] > 0) setClass("unoccupied", "Nije zauzeto", "#411");	
		$('#412').text(arrUnoccupiedPalettesNo41[1]);
		//if(arrUnoccupiedPalettesNo41[1] === 0) {setClass("occupied", "Zauzeto", "#412");}
		//if(arrUnoccupiedPalettesNo41[1] > 0) setClass("unoccupied", "Nije zauzeto", "#412");
		$('#413').text(arrUnoccupiedPalettesNo41[2]);
		//if(arrUnoccupiedPalettesNo41[2] === 0) setClass("occupied", "Zauzeto", "#413");
		//if(arrUnoccupiedPalettesNo41[2] > 0) setClass("unoccupied", "Nije zauzeto", "#413");
		$('#414').text(arrUnoccupiedPalettesNo41[3]);
		//if(arrUnoccupiedPalettesNo41[3] === 0) setClass("occupied", "Zauzeto", "#414");
		//if(arrUnoccupiedPalettesNo41[3] > 0) setClass("unoccupied", "Nije zauzeto", "#414");
		$('#415').text(arrUnoccupiedPalettesNo41[4]);
		//if(arrUnoccupiedPalettesNo41[4] === 0) setClass("occupied", "Zauzeto", "#415");
		//if(arrUnoccupiedPalettesNo41[4] > 0) setClass("unoccupied", "Nije zauzeto", "#415");
		$('#416').text(arrUnoccupiedPalettesNo41[5]);
		//if(arrUnoccupiedPalettesNo41[5] === 0) setClass("occupied", "Zauzeto", "#416");
		//if(arrUnoccupiedPalettesNo41[5] > 0) setClass("unoccupied", "Nije zauzeto", "#416");
		$('#417').text(arrUnoccupiedPalettesNo41[6]);
		//if(arrUnoccupiedPalettesNo41[6] === 0) setClass("occupied", "Zauzeto", "#417");
		//if(arrUnoccupiedPalettesNo41[6] > 0) setClass("unoccupied", "Nije zauzeto", "#417");	


		let arrTotalPalettesNo42 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo42 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo42 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes42.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo42[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo42[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo42[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo42[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo42[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo42[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo42[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo42[i] = arrTotalPalettesNo42[i] - arrOccupiedPalettesNo42[i];
			
		}		

		$('#421').text(arrUnoccupiedPalettesNo42[0]);
		//if(arrUnoccupiedPalettesNo42[0] === 0) setClass("occupied", "Zauzeto", "#421");	
		//if(arrUnoccupiedPalettesNo42[0] > 0) setClass("unoccupied", "Nije zauzeto", "#421");	
		$('#422').text(arrUnoccupiedPalettesNo42[1]);
		//if(arrUnoccupiedPalettesNo42[1] === 0) {setClass("occupied", "Zauzeto", "#422");}
		//if(arrUnoccupiedPalettesNo42[1] > 0) setClass("unoccupied", "Nije zauzeto", "#422");
		$('#423').text(arrUnoccupiedPalettesNo42[2]);
		//if(arrUnoccupiedPalettesNo42[2] === 0) setClass("occupied", "Zauzeto", "#423");
		//if(arrUnoccupiedPalettesNo42[2] > 0) setClass("unoccupied", "Nije zauzeto", "#423");
		$('#424').text(arrUnoccupiedPalettesNo42[3]);
		//if(arrUnoccupiedPalettesNo42[3] === 0) setClass("occupied", "Zauzeto", "#424");
		//if(arrUnoccupiedPalettesNo42[3] > 0) setClass("unoccupied", "Nije zauzeto", "#424");
		$('#425').text(arrUnoccupiedPalettesNo42[4]);
		//if(arrUnoccupiedPalettesNo42[4] === 0) setClass("occupied", "Zauzeto", "#425");
		//if(arrUnoccupiedPalettesNo42[4] > 0) setClass("unoccupied", "Nije zauzeto", "#425");
		$('#426').text(arrUnoccupiedPalettesNo42[5]);
		//if(arrUnoccupiedPalettesNo42[5] === 0) setClass("occupied", "Zauzeto", "#426");
		//if(arrUnoccupiedPalettesNo42[5] > 0) setClass("unoccupied", "Nije zauzeto", "#426");
		$('#427').text(arrUnoccupiedPalettesNo42[6]);
		//if(arrUnoccupiedPalettesNo42[6] === 0) setClass("occupied", "Zauzeto", "#427");
		//if(arrUnoccupiedPalettesNo42[6] > 0) setClass("unoccupied", "Nije zauzeto", "#427");	


		let arrTotalPalettesNo31 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo31 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo31 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes31.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo31[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo31[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo31[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo31[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo31[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo31[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo31[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo31[i] = arrTotalPalettesNo31[i] - arrOccupiedPalettesNo31[i];
			
		}		

		$('#311').text(arrUnoccupiedPalettesNo31[0]);
		//if(arrUnoccupiedPalettesNo31[0] === 0) setClass("occupied", "Zauzeto", "#311");	
		//if(arrUnoccupiedPalettesNo31[0] > 0) setClass("unoccupied", "Nije zauzeto", "#311");	
		$('#312').text(arrUnoccupiedPalettesNo31[1]);
		//if(arrUnoccupiedPalettesNo31[1] === 0) {setClass("occupied", "Zauzeto", "#312");}
		//if(arrUnoccupiedPalettesNo31[1] > 0) setClass("unoccupied", "Nije zauzeto", "#312");
		$('#313').text(arrUnoccupiedPalettesNo31[2]);
		//if(arrUnoccupiedPalettesNo31[2] === 0) setClass("occupied", "Zauzeto", "#313");
		//if(arrUnoccupiedPalettesNo31[2] > 0) setClass("unoccupied", "Nije zauzeto", "#313");
		$('#314').text(arrUnoccupiedPalettesNo31[3]);
		//if(arrUnoccupiedPalettesNo31[3] === 0) setClass("occupied", "Zauzeto", "#314");
		//if(arrUnoccupiedPalettesNo31[3] > 0) setClass("unoccupied", "Nije zauzeto", "#314");
		$('#315').text(arrUnoccupiedPalettesNo31[4]);
		//if(arrUnoccupiedPalettesNo31[4] === 0) setClass("occupied", "Zauzeto", "#315");
		//if(arrUnoccupiedPalettesNo31[4] > 0) setClass("unoccupied", "Nije zauzeto", "#315");
		$('#316').text(arrUnoccupiedPalettesNo31[5]);
		//if(arrUnoccupiedPalettesNo31[5] === 0) setClass("occupied", "Zauzeto", "#316");
		//if(arrUnoccupiedPalettesNo31[5] > 0) setClass("unoccupied", "Nije zauzeto", "#316");
		$('#317').text(arrUnoccupiedPalettesNo31[6]);
		//if(arrUnoccupiedPalettesNo31[6] === 0) setClass("occupied", "Zauzeto", "#317");
		//if(arrUnoccupiedPalettesNo31[6] > 0) setClass("unoccupied", "Nije zauzeto", "#317");	


		let arrTotalPalettesNo32 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo32 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo32 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes32.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo32[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo32[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo32[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo32[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo32[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo32[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo32[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo32[i] = arrTotalPalettesNo32[i] - arrOccupiedPalettesNo32[i];
			
		}		

		$('#321').text(arrUnoccupiedPalettesNo32[0]);
		//if(arrUnoccupiedPalettesNo32[0] === 0) setClass("occupied", "Zauzeto", "#321");	
		//if(arrUnoccupiedPalettesNo32[0] > 0) setClass("unoccupied", "Nije zauzeto", "#321");	
		$('#322').text(arrUnoccupiedPalettesNo32[1]);
		//if(arrUnoccupiedPalettesNo32[1] === 0) {setClass("occupied", "Zauzeto", "#322");}
		//if(arrUnoccupiedPalettesNo32[1] > 0) setClass("unoccupied", "Nije zauzeto", "#322");
		$('#323').text(arrUnoccupiedPalettesNo32[2]);
		//if(arrUnoccupiedPalettesNo32[2] === 0) setClass("occupied", "Zauzeto", "#323");
		//if(arrUnoccupiedPalettesNo32[2] > 0) setClass("unoccupied", "Nije zauzeto", "#323");
		$('#324').text(arrUnoccupiedPalettesNo32[3]);
		//if(arrUnoccupiedPalettesNo32[3] === 0) setClass("occupied", "Zauzeto", "#324");
		//if(arrUnoccupiedPalettesNo32[3] > 0) setClass("unoccupied", "Nije zauzeto", "#324");
		$('#325').text(arrUnoccupiedPalettesNo32[4]);
		//if(arrUnoccupiedPalettesNo32[4] === 0) setClass("occupied", "Zauzeto", "#325");
		//if(arrUnoccupiedPalettesNo32[4] > 0) setClass("unoccupied", "Nije zauzeto", "#325");
		$('#326').text(arrUnoccupiedPalettesNo32[5]);
		//if(arrUnoccupiedPalettesNo32[5] === 0) setClass("occupied", "Zauzeto", "#326");
		//if(arrUnoccupiedPalettesNo32[5] > 0) setClass("unoccupied", "Nije zauzeto", "#326");
		$('#327').text(arrUnoccupiedPalettesNo32[6]);
		//if(arrUnoccupiedPalettesNo32[6] === 0) setClass("occupied", "Zauzeto", "#327");
		//if(arrUnoccupiedPalettesNo32[6] > 0) setClass("unoccupied", "Nije zauzeto", "#327");	


		let arrTotalPalettesNo21 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo21 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo21 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes21.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo21[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo21[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo21[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo21[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo21[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo21[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo21[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo21[i] = arrTotalPalettesNo21[i] - arrOccupiedPalettesNo21[i];
			
		}		

		$('#211').text(arrUnoccupiedPalettesNo21[0]);
		//if(arrUnoccupiedPalettesNo21[0] === 0) setClass("occupied", "Zauzeto", "#211");	
		//if(arrUnoccupiedPalettesNo21[0] > 0) setClass("unoccupied", "Nije zauzeto", "#211");	
		$('#212').text(arrUnoccupiedPalettesNo21[1]);
		//if(arrUnoccupiedPalettesNo21[1] === 0) {setClass("occupied", "Zauzeto", "#212");}
		//if(arrUnoccupiedPalettesNo21[1] > 0) setClass("unoccupied", "Nije zauzeto", "#212");
		$('#213').text(arrUnoccupiedPalettesNo21[2]);
		//if(arrUnoccupiedPalettesNo21[2] === 0) setClass("occupied", "Zauzeto", "#213");
		//if(arrUnoccupiedPalettesNo21[2] > 0) setClass("unoccupied", "Nije zauzeto", "#213");
		$('#214').text(arrUnoccupiedPalettesNo21[3]);
		//if(arrUnoccupiedPalettesNo21[3] === 0) setClass("occupied", "Zauzeto", "#214");
		//if(arrUnoccupiedPalettesNo21[3] > 0) setClass("unoccupied", "Nije zauzeto", "#214");
		$('#215').text(arrUnoccupiedPalettesNo21[4]);
		//if(arrUnoccupiedPalettesNo21[4] === 0) setClass("occupied", "Zauzeto", "#215");
		//if(arrUnoccupiedPalettesNo21[4] > 0) setClass("unoccupied", "Nije zauzeto", "#215");
		$('#216').text(arrUnoccupiedPalettesNo21[5]);
		//if(arrUnoccupiedPalettesNo21[5] === 0) setClass("occupied", "Zauzeto", "#216");
		//if(arrUnoccupiedPalettesNo21[5] > 0) setClass("unoccupied", "Nije zauzeto", "#216");
		$('#217').text(arrUnoccupiedPalettesNo21[6]);
		//if(arrUnoccupiedPalettesNo21[6] === 0) setClass("occupied", "Zauzeto", "#217");
		//if(arrUnoccupiedPalettesNo21[6] > 0) setClass("unoccupied", "Nije zauzeto", "#217");	


		let arrTotalPalettesNo22 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo22 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo22 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes22.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo22[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo22[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo22[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo22[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo22[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo22[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo22[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo22[i] = arrTotalPalettesNo22[i] - arrOccupiedPalettesNo22[i];
			
		}		

		$('#221').text(arrUnoccupiedPalettesNo22[0]);
		//if(arrUnoccupiedPalettesNo22[0] === 0) setClass("occupied", "Zauzeto", "#221");	
		//if(arrUnoccupiedPalettesNo22[0] > 0) setClass("unoccupied", "Nije zauzeto", "#221");	
		$('#222').text(arrUnoccupiedPalettesNo22[1]);
		//if(arrUnoccupiedPalettesNo22[1] === 0) {setClass("occupied", "Zauzeto", "#222");}
		//if(arrUnoccupiedPalettesNo22[1] > 0) setClass("unoccupied", "Nije zauzeto", "#222");
		$('#223').text(arrUnoccupiedPalettesNo22[2]);
		//if(arrUnoccupiedPalettesNo22[2] === 0) setClass("occupied", "Zauzeto", "#223");
		//if(arrUnoccupiedPalettesNo22[2] > 0) setClass("unoccupied", "Nije zauzeto", "#223");
		$('#224').text(arrUnoccupiedPalettesNo22[3]);
		//if(arrUnoccupiedPalettesNo22[3] === 0) setClass("occupied", "Zauzeto", "#224");
		//if(arrUnoccupiedPalettesNo22[3] > 0) setClass("unoccupied", "Nije zauzeto", "#224");
		$('#225').text(arrUnoccupiedPalettesNo22[4]);
		//if(arrUnoccupiedPalettesNo22[4] === 0) setClass("occupied", "Zauzeto", "#225");
		//if(arrUnoccupiedPalettesNo22[4] > 0) setClass("unoccupied", "Nije zauzeto", "#225");
		$('#226').text(arrUnoccupiedPalettesNo22[5]);
		//if(arrUnoccupiedPalettesNo22[5] === 0) setClass("occupied", "Zauzeto", "#226");
		//if(arrUnoccupiedPalettesNo22[5] > 0) setClass("unoccupied", "Nije zauzeto", "#226");
		$('#227').text(arrUnoccupiedPalettesNo22[6]);
		//if(arrUnoccupiedPalettesNo22[6] === 0) setClass("occupied", "Zauzeto", "#227");
		//if(arrUnoccupiedPalettesNo22[6] > 0) setClass("unoccupied", "Nije zauzeto", "#227");	


		let arrTotalPalettesNo11 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo11 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo11 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes11.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo11[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo11[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo11[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo11[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo11[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo11[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo11[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo11[i] = arrTotalPalettesNo11[i] - arrOccupiedPalettesNo11[i];
			
		}		

		$('#111').text(arrUnoccupiedPalettesNo11[0]);
		//if(arrUnoccupiedPalettesNo11[0] === 0) setClass("occupied", "Zauzeto", "#111");	
		//if(arrUnoccupiedPalettesNo11[0] > 0) setClass("unoccupied", "Nije zauzeto", "#111");	
		$('#112').text(arrUnoccupiedPalettesNo11[1]);
		//if(arrUnoccupiedPalettesNo11[1] === 0) {setClass("occupied", "Zauzeto", "#112");}
		//if(arrUnoccupiedPalettesNo11[1] > 0) setClass("unoccupied", "Nije zauzeto", "#112");
		$('#113').text(arrUnoccupiedPalettesNo11[2]);
		//if(arrUnoccupiedPalettesNo11[2] === 0) setClass("occupied", "Zauzeto", "#113");
		//if(arrUnoccupiedPalettesNo11[2] > 0) setClass("unoccupied", "Nije zauzeto", "#113");
		$('#114').text(arrUnoccupiedPalettesNo11[3]);
		//if(arrUnoccupiedPalettesNo11[3] === 0) setClass("occupied", "Zauzeto", "#114");
		//if(arrUnoccupiedPalettesNo11[3] > 0) setClass("unoccupied", "Nije zauzeto", "#114");
		$('#115').text(arrUnoccupiedPalettesNo11[4]);
		//if(arrUnoccupiedPalettesNo11[4] === 0) setClass("occupied", "Zauzeto", "#115");
		//if(arrUnoccupiedPalettesNo11[4] > 0) setClass("unoccupied", "Nije zauzeto", "#115");
		$('#116').text(arrUnoccupiedPalettesNo11[5]);
		//if(arrUnoccupiedPalettesNo11[5] === 0) setClass("occupied", "Zauzeto", "#116");
		//if(arrUnoccupiedPalettesNo11[5] > 0) setClass("unoccupied", "Nije zauzeto", "#116");
		$('#117').text(arrUnoccupiedPalettesNo11[6]);
		//if(arrUnoccupiedPalettesNo11[6] === 0) setClass("occupied", "Zauzeto", "#117");
		//if(arrUnoccupiedPalettesNo11[6] > 0) setClass("unoccupied", "Nije zauzeto", "#117");	


		let arrTotalPalettesNo12 = [102, 102, 102, 102, 102, 102, 102];
		let arrOccupiedPalettesNo12 = [0, 0, 0, 0, 0, 0, 0];
		let arrUnoccupiedPalettesNo12 = [];

		// set occupied palettes		
		arrOccupiedPaletteCodes12.forEach (e => {
			
			switch (e.paletteCode.substring(5, 7)) {
				case '01':
					arrOccupiedPalettesNo12[0]++;
				break;
				case '02':
		            arrOccupiedPalettesNo12[1]++;
				break;
				case '03':
		            arrOccupiedPalettesNo12[2]++;
				break;
				case '04':
		            arrOccupiedPalettesNo12[3]++;
				break;
				case '05':
		            arrOccupiedPalettesNo12[4]++;
				break;
				case '06':
		            arrOccupiedPalettesNo12[5]++;
				break;
				case '07':
		            arrOccupiedPalettesNo12[6]++;
				break;
			}

		});

		for (let i = 0; i < 7; i++) {
			arrUnoccupiedPalettesNo12[i] = arrTotalPalettesNo12[i] - arrOccupiedPalettesNo12[i];
			
		}		

		$('#121').text(arrUnoccupiedPalettesNo12[0]);
		//if(arrUnoccupiedPalettesNo12[0] === 0) setClass("occupied", "Zauzeto", "#121");	
		//if(arrUnoccupiedPalettesNo12[0] > 0) setClass("unoccupied", "Nije zauzeto", "#121");	
		$('#122').text(arrUnoccupiedPalettesNo12[1]);
		//if(arrUnoccupiedPalettesNo12[1] === 0) {setClass("occupied", "Zauzeto", "#122");}
		//if(arrUnoccupiedPalettesNo12[1] > 0) setClass("unoccupied", "Nije zauzeto", "#122");
		$('#123').text(arrUnoccupiedPalettesNo12[2]);
		//if(arrUnoccupiedPalettesNo12[2] === 0) setClass("occupied", "Zauzeto", "#123");
		//if(arrUnoccupiedPalettesNo12[2] > 0) setClass("unoccupied", "Nije zauzeto", "#123");
		$('#124').text(arrUnoccupiedPalettesNo12[3]);
		//if(arrUnoccupiedPalettesNo12[3] === 0) setClass("occupied", "Zauzeto", "#124");
		//if(arrUnoccupiedPalettesNo12[3] > 0) setClass("unoccupied", "Nije zauzeto", "#124");
		$('#125').text(arrUnoccupiedPalettesNo12[4]);
		//if(arrUnoccupiedPalettesNo12[4] === 0) setClass("occupied", "Zauzeto", "#125");
		//if(arrUnoccupiedPalettesNo12[4] > 0) setClass("unoccupied", "Nije zauzeto", "#125");
		$('#126').text(arrUnoccupiedPalettesNo12[5]);
		//if(arrUnoccupiedPalettesNo12[5] === 0) setClass("occupied", "Zauzeto", "#126");
		//if(arrUnoccupiedPalettesNo12[5] > 0) setClass("unoccupied", "Nije zauzeto", "#126");
		$('#127').text(arrUnoccupiedPalettesNo12[6]);
		//if(arrUnoccupiedPalettesNo12[6] === 0) setClass("occupied", "Zauzeto", "#127");
		//if(arrUnoccupiedPalettesNo12[6] > 0) setClass("unoccupied", "Nije zauzeto", "#127");
		
		jQuery.ajaxSetup({async:true});
	});
					
}); // document ready END

function setClass(styleClass, title, selectedEl) {
	$(selectedEl).removeClass('unoccupied occupied processing locked');
	$(selectedEl).addClass(styleClass);
	$(selectedEl).attr('title', title);	
}

function average(arr, avr){
	//let avrElement = arr[0];
	let index = 0;	
	for(let i=1; i<16; i++) {
		if(Math.abs(arr[i] - avr) < Math.abs(arr[index] - avr) ){
			//avrElement = arr[i];
			index = i;
		}
	}
	return index;	
}
