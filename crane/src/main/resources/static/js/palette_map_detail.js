$(document).ready(() => {

	/**
	 * Set red occupied palettes 
	 */	
	jQuery.ajaxSetup({async:false});
	let arrOccupiedPaletteCodes = [];
	
	$.get( "http://localhost:33377/api/v1/palette/allOccupiedByRowId/" + $('#warehouseRowId').text(), function( data ) {
		for (i= 0; i < data.data.length; i++) {
			arrOccupiedPaletteCodes.push(
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
	
	/**
	 * Gets fixed List<Integer> with 3 elements, where: 1st is totalCnt, 2nd is occupiedCnt, 3rd is freeCnt
	 */
	$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + $('#warehouseRowId').text(), function( data ) {
		$('#totalNo').text(data.data[0]);
		$('#occupiedNo').text(data.data[1]);
		$('#unoccupiedNo').text(data.data[2]);
	});
	
    /**
     * Builds and appends main wh columns and rows
     **/
    let whid = $('#warehouseRowId').text();
    let columnNumber = 34//9; // TODO: make configurable in properties file and pull it from there
    let oddEvenNo = whid % 2 == 0 ? 2 : 1;
    let strDivStart = '<div class="column-group">';
    let strDivEnd = '</div>';

    let strEl1 = "";
    for (let colId = 0; colId < columnNumber; colId++) {
        let x = whid.toString().charAt(0).padStart(2, '0');
        let y = 0;
        let z = 0;
        strEl1 = strDivStart;
        for (let i = 0; i < 21; i++) {
            y = (2*(i % 3) + oddEvenNo) + (colId*6);
            z = Math.ceil((21-i)/3).toString().padStart(2, '0');
            strEl1 += '<div class="p-cell unoccupied" title="Nije zauzeto" id="' + x + '' + String(y).padStart(3, '0') + '' + z + '"'
					// + ' data-status-id="1" data-client-id="123" data-client-name="Goran" data-length="0" data-width="0" data-height="0" data-process-date="2025-01-01"'  
					+ '>'
					+ '<a href="#">'+ x + ' ' + String(y).padStart(3, '0') + ' ' + z +'</a></div>';
        }
        strEl1 += strDivEnd;
        $('#container1').append(strEl1);
    }

    /**
     * Builds and appends bottom ordering of wh columns
     **/
     let oddEvenNo1 = whid % 2 == 0 ? 2 : 1;
    let strEl2 = "";
    let strCol0DivStart = '<div class="col-0">'
    for (let i = 0; i < columnNumber; i++) {
        strEl2 = strCol0DivStart;
        for (let j = 0; j < 3; j++) {
            strEl2 += '<div>' + oddEvenNo1 + '</div>';
            oddEvenNo1+=2;
        }
        strEl2 += strDivEnd;
        $('#row-column-enum').append(strEl2);
    }


    let totalNo = columnNumber * 21;
    let occupiedNo = arrOccupiedPaletteCodes.length;
    let unoccupiedNo = totalNo - occupiedNo;
    $('#totalNo').text(totalNo);
    $('#occupiedNo').text(occupiedNo);
    $('#unoccupiedNo').text(unoccupiedNo);
	
    	
	adjustZoom();
	window.addEventListener('resize', adjustZoom);
	
	$(".p-cell").on('click', (element) => {
		
		const elTitle = $('#toastTitle');
		const elemToast = document.getElementById('liveToast');
		const toast = new bootstrap.Toast(elemToast);

		let elPalette = $(element.currentTarget);
		let paletteId = elPalette.attr('id');
		$('#paletteId').text(paletteId);
		
		elTitle.text(
			  "RED: "     + paletteId.substring(0, 2)
			+ " DUBINA: " + paletteId.substring(2, 5)
			+ " VISINA: " + paletteId.substring(5, 7)
		);
		
		$('#toast-palette-id').text(paletteId);
		$('#toast-client-id').text(elPalette.attr('data-client-id') == null ? '' : elPalette.attr('data-client-id'));
		$('#toast-client-name').text(elPalette.attr('data-client-name') == null ? '' : elPalette.attr('data-client-name'));
		$('#toast-length').text(elPalette.attr('data-length') == null ? '' : elPalette.attr('data-client-length'));
		$('#toast-width').text(elPalette.attr('data-width') == null ? '' : elPalette.attr('data-client-width'));
		$('#toast-height').text(elPalette.attr('data-height') == null ? '' : elPalette.attr('data-client-height'));
		$('#toast-process-date').text(elPalette.attr('data-process-date') == null ? '' : elPalette.attr('data-process-date'));

		/** Za sada necu ogranicavati usera. Moci ce da sve kikne		
		switch (parseInt(elPalette.attr('data-status-id'))) {
			case 3: // Smeštena
				$('#btnSetFree').prop('disabled', false);
			break;
		}
		**/
		
		toast.show();
	});

	// set occupied palettes	
	arrOccupiedPaletteCodes.forEach (e => {
		let styleClass = "unoccupied";
		let title = "Nije zauzeto'";
		let selectedEl = '#' + e.paletteCode;
		
		$(selectedEl).removeClass('unoccupied occupied processing locked');
		
		switch (e.statusId) {
			case 1:
				styleClass = "unoccupied";
				title = 'Nije zauzeto';
			break;
			case 2:
				styleClass = "processing";
				title = 'Procesira se';
			break;
			case 3:
				styleClass = "occupied";
				title = 'Zauzeto';
			break;
			case 4:
				styleClass = "processing";
				title = 'Procesira se';
			break;
			case 5:
				styleClass = "unoccupied";
				title = 'Nije zauzeto';
			break;
			case 6:
				styleClass = "processing";
				title = 'Procesira se';
			break;
			case 8:
				styleClass = "locked";
				title = 'Zaključano';
			break;
		}
		
		$(selectedEl).addClass(styleClass);
		$(selectedEl).attr('title', title);
		$(selectedEl).attr('data-status-id', e.statusId);
		$(selectedEl).attr('data-client-id', e.clientId);
		$(selectedEl).attr('data-client-name', e.clientName);
		$(selectedEl).attr('data-length', e.length);
		$(selectedEl).attr('data-width', e.width);
		$(selectedEl).attr('data-height', e.height);
	});
	

	$('#btnSetOccupied').on('click', (element) => {
		let paletteId = $('#paletteId').text();
		let elPalette = $('#' + paletteId);
		
		/*let foundElement = arrOccupiedPaletteCodes.find(item => item.paletteCode === paletteId);
		if(!foundElement || (foundElement && foundElement.statusId !== 2)) {
			let occupiedNo = $('#occupiedNo');
			occupiedNo.text(parseInt(occupiedNo.text()) + 1);
			let unoccupiedNo = $('#unoccupiedNo');
			unoccupiedNo.text(parseInt(unoccupiedNo.text()) - 1);
		}*/

		$.get( "http://localhost:33377/api/v1/palette/setOccupied/" + paletteId, function( data ) {
			elPalette.removeClass('unoccupied occupied processing locked');
			elPalette.addClass('occupied');
		});
		
		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + $('#warehouseRowId').text(), function( data ) {
			//$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);
			$('#unoccupiedNo').text(data.data[2]);
		});		
	});

	$('#btnSetFree').on('click', (element) => {
		let paletteId = $('#paletteId').text();
		let elPalette = $('#' + paletteId);
		
		/*let foundElement = arrOccupiedPaletteCodes.find(item => item.paletteCode === paletteId);
		if(foundElement && foundElement.statusId === 2) {
			let occupiedNo = $('#occupiedNo');
			occupiedNo.text(parseInt(occupiedNo.text()) - 1);
			let unoccupiedNo = $('#unoccupiedNo');
			unoccupiedNo.text(parseInt(unoccupiedNo.text()) + 1);	
		}*/	

		$.get( "http://localhost:33377/api/v1/palette/setFree/" + paletteId, function( data ) {
			elPalette.removeClass('unoccupied occupied processing locked');
			elPalette.addClass('unoccupied');
		});
		
		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + $('#warehouseRowId').text(), function( data ) {
			//$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);
			$('#unoccupiedNo').text(data.data[2]);
		});		
	});
	
	$('#btnSetLocked').on('click', (element) => {
		let paletteId = $('#paletteId').text();
		let elPalette = $('#' + paletteId);
		
		/*let foundElement = arrOccupiedPaletteCodes.find(item => item.paletteCode === paletteId);
		if(foundElement && foundElement.statusId === 2) {
			let occupiedNo = $('#occupiedNo');
			occupiedNo.text(parseInt(occupiedNo.text()) - 1);
			let unoccupiedNo = $('#unoccupiedNo');
			unoccupiedNo.text(parseInt(unoccupiedNo.text()) + 1);	
		}*/	

		$.get( "http://localhost:33377/api/v1/palette/setLocked/" + paletteId, function( data ) {
			elPalette.removeClass('unoccupied occupied processing locked');
			elPalette.addClass('locked');
		});
		
		$.get( "http://localhost:33377/api/v1/palette/countTotalStoredFreeByRowId/" + $('#warehouseRowId').text(), function( data ) {
			//$('#totalNo').text(data.data[0]);
			$('#occupiedNo').text(data.data[1]);
			$('#unoccupiedNo').text(data.data[2]);
		});		
	});	

	
	//TODO: set green on success
	$('#btnFetchPalette').on('click', (element) => {
		let paletteId = $('#paletteId').text();
		let elPalette = $('#' + paletteId);

		$.get( "http://localhost:33377/api/v1/palette/fetch/" + paletteId, function( data ) {
			elPalette.removeClass('unoccupied occupied processing locked');
			elPalette.addClass('unoccupied');
		});
	});

	/**
	 * 
	 */
	$('.toast-buttons-container > button').on('click', (element) => {
		const elemToast = document.getElementById('liveToast');
		const toast = new bootstrap.Toast(elemToast);
		toast.hide();
	});
	
}); //document.ready END

function adjustZoom() {
    const widthScale = window.innerWidth / 1920;
    const heightScale = window.innerHeight / 1080;
    const scale = Math.min(widthScale, heightScale);
    $('#container-detail').css('transformOrigin', 'top left');
    $('#container-detail').css('transform', `scale(${scale})`);
}
