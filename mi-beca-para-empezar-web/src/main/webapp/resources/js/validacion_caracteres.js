function IsNumber(e) {

	tecla = (document.all) ? e.keyCode : e.which;

	if ((tecla == 8) || (tecla == 0))
		return true;

	// Solo acepta números
	patron = /\d/;

	te = String.fromCharCode(tecla);

	return patron.test(te);

}

function IsNumberDouble(e) {

	tecla = (document.all) ? e.keyCode : e.which;

	if ((tecla == 8) || (tecla == 0))
		return true;

	patron = /[\d.]/; // Solo acepta números

	te = String.fromCharCode(tecla);

	return patron.test(te);

}

function IsLetra(e) {

	tecla = (document.all) ? e.keyCode : e.which;

	if ((tecla == 8) || (tecla == 0))
		return true;

	// Solo acepta números, letras, caracteres permitidos
	patron = /^[a-zA-Z\ áéíóú\u00c1\u00c9\u00cd\u00d3\u00da\u00f1\u00d1\u00FC\u00DC\u0027]+$/;

	te = String.fromCharCode(tecla);

	return patron.test(te);

}

function IsLetraNumero(e) {

	tecla = (document.all) ? e.keyCode : e.which;

	if ((tecla == 8) || (tecla == 0))
		return true;

	// Solo acepta números, letras, caracteres permitidos
	patron = /^[a-zA-Z\  /\d/ áéíóú\u00c1\u00c9\u00cd\u00d3\u00da\u00f1\u00d1\u00FC\u00DC]+$/;

	te = String.fromCharCode(tecla);

	return patron.test(te);

}

function IsLetraNumeroFolio(obj, e) {
	tecla = (document.all) ? e.keyCode : e.which;
	if ((tecla == 8) || (tecla == 0))
		return true;
	if (obj.value.length == 9 && (tecla == 80 || tecla == 112))
		return true;
	patron = /^[a-zA-Z\  /\d/]+$/;
	te = String.fromCharCode(tecla);
	return patron.test(te);
}

function IsLetraNumeroGuionParentesis(n, e) {
	tecla = (document.all) ? e.keyCode : e.which;
	if ((tecla == 8) || (tecla == 0))
		return true;
	if (n == 1) {
		// Solo acepta números, letras, caracteres permitidos
		patron = /^[a-zA-Z-\ /\d/ áéíóú\u00c1\u00c9\u00cd\u00d3\u00da\u00f1\u00d1\u00FC\u00DC]+$/;
	} else if (n == 2) {
		// Solo acepta números, letras, caracteres permitidos
		patron = /^[a-zA-Z-()\ /\d/ áéíóú\u00c1\u00c9\u00cd\u00d3\u00da\u00f1\u00d1\u00FC\u00DC]+$/;
	}

	te = String.fromCharCode(tecla);
	return patron.test(te);
}

function IsCaracter(e) {

	tecla = (document.all) ? e.keyCode : e.which;

	if ((tecla == 8) || (tecla == 0))
		return true;

	// Solo acepta números, letras, caracteres permitidos
	patron = /[\wñÑ.,;/$%+*#@:\-_\s]/;

	te = String.fromCharCode(tecla);

	return patron.test(te);

}

function IsDoubleCorrecto(e, x) {
	tecla = (document.all) ? x.keyCode : x.which;
	if ((tecla == 8) || (tecla == 0))
		return true;
	var patron = /^\d+\.(\d+)?$/; // Solo acepta números
	patr = /^\d+\.?/;
	pat = /\d/;
	patt = /\./;
	te = String.fromCharCode(tecla);
	if (!pat.test(te) && !patt.test(te))
		return false;
	if (pat.test(te))
		return true;
	else if (patt.test(te) && patron.test(e.value))
		return false;
	else
		return patr.test(e.value);
}

function llenarCero(objeto) {
	if (objeto.value.length == 0)
		objeto.value = 0;

}

function IsValidoHora(e, objeto) {
	tecla = (document.all) ? e.keyCode : e.which;
	if ((tecla == 8) || (tecla == 0))
		return true;
	patron = /\d/;
	te = String.fromCharCode(tecla);
	if (patron.test(te)
			&& ((objeto.value.length == 0 && te <= 2)
					|| (objeto.value.length == 1 && objeto.value == 2 && te <= 3) || (objeto.value.length == 1 && objeto.value < 2)))
		return true;
	else
		return false;
}

function IsValidoMinutos(e, objeto) {
	tecla = (document.all) ? e.keyCode : e.which;
	if ((tecla == 8) || (tecla == 0))
		return true;
	patron = /\d/;
	te = String.fromCharCode(tecla);
	if (patron.test(te)
			&& ((objeto.value.length == 0 && te <= 5) || (objeto.value.length == 1)))
		return true;
	else
		return false;
}
function maximoHora(objeto) {
	if (parseInt(objeto.value) > 23)
		objeto.value = 23;
}

function maximoMinutos(objeto) {
	if (parseInt(objeto.value) > 59)
		objeto.value = 59;
}
function esTamanoCorrecto(e, id) {
	if (e.value.length == 2)
		id.focus();
}

function autoTab(origen, destino) {
	if (origen.getAttribute
			&& origen.value.length == origen.getAttribute("maxlength"))
		destino.focus();
}

function ContarCaracteres(field, count, max) {
	// alert("campo-> "+field+" contador-> "+count);

	if (field.value.length > max)

		field.value = field.value.substring(0, max);
	else

		count.value = max - field.value.length;
}

function contarNumCaracteres(field, max) {
	if (field.value.length > max)
		field.value = field.value.substring(0, max);
}

function validarLongitud(lon) {
	var txt = document
			.getElementById('frmCapturarCaratula:fldMensaje:txtObservaciones').value;
	return lon > txt.length;
}
