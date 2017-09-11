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
            var data = {};

            var formData = $(form).serialize();
            formData.split('&').forEach(function (value) {
                var map = value.split('=');
                data[map[0]] = map[1];
            });

            if (!$('#motivoDisciplinas').hasClass('hidden')) {
                var disciplinas = [];
                var disciplina = {};
                var requerimentoDisciplina = {};

                var options = $('select[name=selDisciplinas2] option');
                var values = $.map(options,function(option) {
                    return option.value;
                });
                values.forEach(function (value) {
                    disciplina = {"id": parseInt(value), "codigo": '', "nome": ''};
                    requerimentoDisciplina = {"id": "", "professor": "", "data": "null", "disciplina": disciplina};
                    disciplinas.push(requerimentoDisciplina);
                });

                if (disciplinas.length > 0) {
                    data["disciplinas"] = disciplinas;
                }
            }
            $.ajax({
                type : $(form).attr('method'),
                url  : $(form).attr('action'),
                contentType : 'application/json; charset=utf-8',
                dataType: 'json',
                data : JSON.stringify(data),
                success : function(data) {
                    if (data["situacao"] = "OK") {
                        swal({
                            title : "Salvo!",
                            text : data["mensagem"],
                            type : "success",
                            showCancelButton : false,
                            confirmButtonText : "Ok",
                            closeOnConfirm : false
                        }, function() {
                            window.location = '/ProjetoJSP/';
                        });
                    } else {
                        swal("Falhou!", data["mensagem"], "error");
                    }

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
    $('#disciplinas').bootstrapDualListbox({
        helperSelectNamePostfix: "selDisciplinas",
        filterTextClear: "Exibir todas",
        filterPlaceHolder: "Informe o código ou nome da disciplina",
        removeSelectedLabel: "Remover seleção",
        removeAllLabel: "Remover todas",
        moveSelectedLabel: "Adicionar seleção",
        moveAllLabel: "Adicionar todas",
        moveOnSelect: false,
        selectedListLabel: "Disciplinas selecionadas",
        nonSelectedListLabel: "Disciplinas",
        infoText: 'Exibindo {0} disciplinas',
        infoTextFiltered: '<span class="label label-warning">Filtrado</span> {0} de {1}',
        infoTextEmpty: "Lista vazia",
        selectorMinimalHeight: 160
    });
});