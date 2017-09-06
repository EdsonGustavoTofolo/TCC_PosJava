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
            if ((motivoId != 9) && !$('#motivo9').hasClass('hidden')) { //se nao for 2o. chamada e a div do motivo9 nao estiver invisivel
                $('#motivo9').addClass('hidden');
            } else if (motivoId == 21) { //Convalidação
                //TODO ver para habilitar os campos aqui ou abrir outra página
            } else if (motivoId == 9) {//2o. chamada
                $('#motivo9').removeClass('hidden');
                validador.resetForm();
            }
        }
    });
    /*
    //----[ TYPEAHEAD DOS MOTIVOS ] -----
    // var $input = ;
    $("#motivoTxt").typeahead({
        source: [
            {id:  1, name: 'Afastamento para estudos no exterior'},
            {id:  2, name: 'Atividades Domiciliares'},
            {id:  3, name: 'Avaliação Antecipada'},
            {id:  4, name: 'Avaliação Diferenciada'},
            {id:  5, name: 'Cancelamento de disciplinas'},
            {id:  6, name: 'Declaração de Matrícula'},
            {id:  7, name: 'Declaração de Provável Formando'},
            {id:  8, name: 'Desistência do Curso'},
            {id:  9, name: '2ª chamada de prova'},
            {id: 10, name: 'Diploma (2ª via)'},
            {id: 11, name: 'Histórico Atualizado'},
            {id: 12, name: 'Histórico de Conclusão do Ensino Superior'},
            {id: 13, name: 'Matrícula em Atividades Complementares'},
            {id: 14, name: 'Matrícula em Estágio Supervisionado'},
            {id: 15, name: 'Matrícula nas disciplinas'},
            {id: 16, name: 'Mudança para as turmas'},
            {id: 17, name: 'Planos de Ensino/Ementas'},
            {id: 18, name: 'Trancamento Total da Matrícula'},
            {id: 19, name: '2ª via do crachá'},
            {id: 20, name: 'Outros'},
            {id: 21, name: 'Convalidação'}
        ],
        autoSelect: true
    });
    $("#motivoTxt").change(function() {
        var motivo = $("#motivoTxt").typeahead("getActive");
        $('#motivo').val(motivo.id);
        if (motivo) { // Some item from your model is active!
            if ((motivo.id != 9) && !$('#motivo9').hasClass('hidden')) { //se nao for 2o. chamada e a div do motivo9 nao estiver invisivel
                $('#motivo9').addClass('hidden');
            }

            if (motivo.id == 21) { //Convalidação
                //TODO ver para habilitar os campos aqui ou abrir outra página
            } else if (motivo.id == 9) {//2o. chamada
                $('#motivo9').removeClass('hidden');
            }
        } else {
            // Nothing is active so it is a new value (or maybe empty value)
        }
    });*/
});