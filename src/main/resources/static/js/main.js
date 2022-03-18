/**
 * 
 */

$(document).ready(function(){
	$('.nBtn, .table .eBtn').on('click',function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		var text = $(this).text();
		if(text=='Edit'){
			
		
		$.get(href,function(cliente,status){
			$('.myForm #ragioneSociale').val(cliente.ragioneSociale);
			$('.myForm #partitaIva').val(cliente.partitaIva);
			$('.myForm #email').val(cliente.email);
			$('.myForm #dataUltimoContatto').val(cliente.dataUltimoContatto);
			$('.myForm #fatturatoAnnuale').val(cliente.fatturatoAnnuale);
			$('.myForm #pec').val(cliente.pec);
			$('.myForm #telefono').val(cliente.telefono);
			$('.myForm #emailContatto').val(cliente.emailContatto);
			$('.myForm #nomeContatto').val(cliente.nomeContatto);
			$('.myForm #cognomeContatto').val(cliente.cognomeContatto);
			$('.myForm #telefonoContatto').val(cliente.telefonoContatto);
			$('.myForm #tipo').val(cliente.tipo);
		});
		
		$('.myForm #exampleModal').modal();
		}else {
			$('.myForm #ragioneSociale').val('');
			$('.myForm #partitaIva').val('');
			$('.myForm #email').val('');
			$('.myForm #dataUltimoContatto').val('');
			$('.myForm #fatturatoAnnuale').val('');
			$('.myForm #pec').val('');
			$('.myForm #telefono').val('');
			$('.myForm #emailContatto').val('');
			$('.myForm #nomeContatto').val('');
			$('.myForm #cognomeContatto').val('');
			$('.myForm #telefonoContatto').val('');
			$('.myForm #tipo').val('');
			$('.myForm #exampleModal').modal();
		}
	});
	$('.table .delBtn').on('click', function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		$('#myModal #delRef').attr('href',href);
		$('#myModal').modal();
	});
});