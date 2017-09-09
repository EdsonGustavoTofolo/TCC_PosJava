/**
 * Created by Edson on 11/07/2017.
 */
$(document).ready(function () {
    //-----[ VALIDA O FORM ANTES DO SUBMIT ]----
    var validador = $('#frm').validate({
        rules: {
            motivo: {
                required: true
            },
            disciplina: {
                required: true
            },
            professor: {
                required: true
            },
            data: {
                required: true
            }
        },
        submitHandler: function (form) {
            $.ajax({
                type : $(form).attr('method'),
                url  : $(form).attr('action'),
                data : $(form).serialize(),
                success : function(data) {
                    swal({
                        title : "Salvo!",
                        text : "Registro salvo com sucesso.",
                        type : "success",
                        showCancelButton : false,
                        confirmButtonText : "Ok",
                        closeOnConfirm : false
                    }, function() {
                        window.location = '/index';
                    });
                },//Fim success
                error : function() {
                    swal("Falhou!", "Falha ao salvar registro.", "error");
                }
            });//Fim ajax
            return false;
        }
    });
    //-----[ MASCARA O CAMPO E USA UM DATEPICKER ] -----
    $('#data').datepicker({
        language: "pt-BR",
        todayBtn: "linked",
        keyboardNavigation: true,
        forceParse: true,
        calendarWeeks: true,
        autoclose: true,
        format: "dd/mm/yyyy",
        todayHighlight: true
    });
    $('#data').mask("99/99/9999");

    //------[ SELECAO DE MOTIVOS PARA REQUERIMENTO ] ----------
    $('#motivo').select2();
    $("#motivo").on("select2:select", function (e) {
        // console.log(e.params['data'].id);
        var motivoId = e.params['data'].id;
        if (motivoId > 0) {
            if ((motivoId != 9) && !$('#motivo9').hasClass('hidden')) {
                $('#motivo9').addClass('hidden');
            }

            if ((motivoId != 5) && !$('#motivoDisciplinas').hasClass('hidden')) {
                $('#motivoDisciplinas').addClass('hidden');
            }

            if (motivoId == 5) {//cancelamento das disciplinas
                $('#motivoDisciplinas').removeClass('hidden');
            } else  if (motivoId == 9) {//2o. chamada
                $('#motivo9').removeClass('hidden');
            } else if (motivoId == 21) { //Convalidação
                //TODO ver para habilitar os campos aqui ou abrir outra página
            }

            validador.resetForm();
        }
    });

    //-------[ DUAL LIST BOX ] ------
    $('.dual_select').bootstrapDualListbox({
        selectorMinimalHeight: 160
    });
});