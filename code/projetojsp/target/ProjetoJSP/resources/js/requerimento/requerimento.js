/**
 * Created by Edson on 11/07/2017.
 */
$(document).ready(function () {
    $.fn.select2.defaults.set( "theme", "bootstrap" );
    $.fn.select2.defaults.set( "width", "100%" );

    $.validator.addMethod("disciplinasSelecionadas",function (value,element){
        return !$('#motivoDisciplinas').hasClass('hidden') && $('select[name=selDisciplinas2] option').length > 0;
    }, 'Selecione uma ou mais disciplinas!');

    $.validator.addMethod("intNumber",function (value,element){
        return ($.isNumeric(value)) && (Math.floor(+value) == +value);//(value).match(/^\d+$/)
    }, 'Por favor, informe somente número inteiro!');

    $.validator.addMethod("numbergt0",function (value,element){
        return ($.isNumeric(value)) && (parseFloat(value) > 0);
    }, 'Informe valor maior que zero(0)!');

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
            },
            selDisciplinas2: {
                disciplinasSelecionadas: true
            },
            disciplinaConvalidacao: {
                required: true,
                maxlength: 100
            },
            cargaHoraria: {
                required: true,
                maxlength: 3,//sempre será número inteiro positivo
                number: true,
                intNumber: true,
                numbergt0: true
            },
            nota: {
                required: true,
                number: true,
                max: 10.00,
                numbergt0: true
            },
            frequencia: {
                required: true,
                number: true,
                max: 100.00,
                numbergt0: true
            },
            notaFinal: {
                required: true,
                number: true,
                max: 10.00,
                numbergt0: true
            },
            freqFinal: {
                required: true,
                number: true,
                max: 100.00,
                numbergt0: true
            }
        }
    });

    //--[ FAZ O SUBMIT COM O ENVIO DOS ARQUIVOS ]----
    Dropzone.options.dropzoneForm = {
        url: "/ProjetoJSP/requerimento/salvar",
        uploadMultiple: true,
        autoProcessQueue: false,
        paramName: "file", // The name that will be used to transfer the file
        maxFilesize: 2, // MB
        maxFiles: 5,
        parallelUploads: 5,
        addRemoveLinks: true,
        dictCancelUpload: "Cancelar envio",
        dictRemoveFile: "Excluir",
        dictInvalidFileType: "Tipo de arquivo não permitido!",
        dictFileTooBig: "Arquivo muito grande. ({{filesize}}MiB). Máximo: {{maxFilesize}}MiB.",
        dictMaxFilesExceeded: "Número máximo de arquivos atingido! Máximo: {{maxFiles}}.",
        dictDefaultMessage: "<strong>Arraste e solte os arquivos aqui!</strong>" +
        "</br>Anexar documentação comprabatória dos motivos alegados.",
        init: function () {
            var myDropzone = this;
            $("#salvar").click(function (e) {
                e.preventDefault();
                e.stopPropagation();

                if ($('#frm').valid()) {
                    if (myDropzone.getQueuedFiles().length > 0) {
                        myDropzone.processQueue();
                    } else {
                        myDropzone.uploadFiles([]); //send empty
                    }
                }
            });
        },
        // sending: function(file, xhr, formData) {},
        // processing: function (file) {},
        // success: function (file, data) {},
        sendingmultiple: function (files, xhr, formData) {
            var dataJSON = {};
            var disciplinas = [];
            var disciplina = {};
            var requerimentoDisciplina = {};
            var professor = {};

            dataJSON["id"] = $('#id').val();
            dataJSON["motivo"] = $('#motivo').val();
            dataJSON["observacao"] = $('#observacao').val();

            if (!$('#motivoDisciplinas').hasClass('hidden')) {
                var options = $('select[name=selDisciplinas2] option');
                var values = $.map(options,function(option) {
                    return option.value;
                });
                values.forEach(function (value) {
                    disciplina = {"id": parseInt(value), "codigo": '', "nome": ''};
                    requerimentoDisciplina = {"id": "", "professor": null, "dataProva": null, "disciplina": disciplina};
                    disciplinas.push(requerimentoDisciplina);
                });

                if (disciplinas.length > 0) {
                    dataJSON["disciplinas"] = disciplinas;
                }
            } else if (!$('#motivo9').hasClass('hidden')) {
                var professorId = $("#professor").val();
                var data = $("#data").val();
                var disciplinaId = $("#disciplina").val();
                var date = data.split("/");
                date = new Date(date[2], date[1] - 1, date[0]);

                professor = {"id": professorId};

                disciplina = {"id": parseInt(disciplinaId), "codigo": '', "nome": ''};
                requerimentoDisciplina = {"id": "", "professor": professor, "dataProva": date, "disciplina": disciplina};
                disciplinas.push(requerimentoDisciplina);

                dataJSON["disciplinas"] = disciplinas;
            } else if (!$('#motivo21').hasClass('hidden')) {
                var itemsConvalidacao = [];

                $(".itemConvalidacao").each(function (index, element) {
                    var disciplinaUtfprSelect = $(element).find(".disciplinaUtfpr").find("select");
                    var disciplinaConvalidacaoInput = $(element).find(".disciplinaConvalidacao").find("input");
                    var cargaHorariaInput = $(element).find(".cargaHoraria").find("input");
                    var notaInput = $(element).find(".nota").find("input");
                    var frequenciaInput = $(element).find(".frequencia").find("input");
                    var notaFinalInput = $(element).find(".notaFinal").find("input");
                    var freqFinalInput = $(element).find(".freqFinal").find("input");
                    var dispensadoSelect = $(element).find(".dispensado").find("select");

                    var disciplinaUtfpr = disciplinaUtfprSelect.select2("val");
                    var disciplinaConvalidacao = disciplinaConvalidacaoInput.val();
                    var cargaHoraria = cargaHorariaInput.val();
                    var nota = notaInput.val();
                    var frequencia = frequenciaInput.val();
                    var notaFinal = notaFinalInput.val();
                    var freqFinal = freqFinalInput.val();
                    var dispensado = dispensadoSelect.select2("val");

                    var itemConvalidacao = {
                        "disciplinaUtfpr": {"id": parseInt(disciplinaUtfpr)},
                        "disciplinaConvalidacao": disciplinaConvalidacao,
                        "cargaHoraria": parseInt(cargaHoraria),
                        "nota": parseFloat(nota),
                        "frequencia": parseFloat(frequencia),
                        "notaFinal": parseFloat(notaFinal),
                        "freqFinal": parseFloat(freqFinal),
                        "dispensado": dispensado
                    };

                    itemsConvalidacao.push(itemConvalidacao);
                });
                dataJSON["convalidacoes"] = itemsConvalidacao;
            }
            formData.append('requerimento', new Blob([JSON.stringify(dataJSON)], {type: "application/json"}));
        },
        processingmultiple: function (files) {
            $.blockUI({message: 'Gravando...'});
        },
        successmultiple: function (files, data) {
            $.unblockUI();
            if (data.state == "OK") {
                swal({
                    title : "Salvo!",
                    text : data.message,
                    type : "success",
                    confirmButtonText : "Ok",
                    timer: 3000,
                    showCancelButton : false,
                    allowEscapeKey: false,
                    allowOutsideClick: false,
                }).then(function () {
                    window.location = '/ProjetoJSP/requerimento/list';
                }, function (dismiss) {
                    if (dismiss === 'timer') {
                        window.location = '/ProjetoJSP/requerimento/list';
                    }
                }).catch(swal.noop); // esse catch evita erro no console do browser;
            } else {
                swal("Falhou!", data.message, "error").catch(swal.noop); // esse catch evita erro no console do browser;
            }
        },
        canceled: function (file) {
            $.unblockUI();
        },
        error: function (file, errorMessage, xhr) {
            $.unblockUI();
            swal("Falhou!", errorMessage, "error").catch(swal.noop); // esse catch evita erro no console do browser;
        }
    };

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

    //-------[ DUAL LIST BOX ] ------
    var disciplinasSelect = $('#disciplinas').bootstrapDualListbox({
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

    //------[ SELECAO DE PROFESSOR ]-----
    $("#professor").select2();

    //------[ SELECAO DO STATUS ] -----
    $("#status").select2();
    $("#status").on("select2:select", function (e) {
        var statusId = e.params['data'].id;
        var requerimentoId = $("#id").val();

        changeStatus($("#requerimentoObs"), parseInt(statusId), requerimentoId,
            function(data) {
                if (data.state == "OK") {
                    window.location = '/ProjetoJSP/requerimento/list';
                } else {
                    swal("Falhou!", data.message, "error").catch(swal.noop);
                }
            });
    });

    //------[ SELECAO DE CURSOS ] --------
    $("#curso").select2();
    $("#curso").on("select2:select", function (e) {
        // var cursoId = e.params['data'].id;
        if (!$('#motivo9').hasClass('hidden')) {
            buscarDisciplinas();
        } else if (!$('#motivoDisciplinas').hasClass('hidden')) {
            buscarMultiselecaoDisciplinas();
        } else if (!$('#motivo21').hasClass('hidden')) {
            buscarDisciplinasItemsConvalidacao();
        }
    });

    //------[ SELECAO DE MOTIVOS PARA REQUERIMENTO ] ----------
    $('#motivo').select2();
    $("#motivo").on("select2:select", function (e) {
        var motivoId = e.params['data'].id;

        if (motivoId > 0) {
            if ((motivoId != 9) && !$('#motivo9').hasClass('hidden')) {
                $('#motivo9').addClass('hidden');
            }

            //Cancelamento das disciplinas || Matrícula nas disciplinas || Planos de Ensino/Ementas das disciplinas
            if (!exibirMultiselecaoDeDisciplinas(motivoId) && !$('#motivoDisciplinas').hasClass('hidden')) {
                $('#motivoDisciplinas').addClass('hidden');
            }

            if (motivoId != 21 && !$('#motivo21').hasClass('hidden')) {
                $('#motivo21').addClass('hidden');
            }

            if (exibirMultiselecaoDeDisciplinas(motivoId)) {//cancelamento das disciplinas ou matrícula nas disciplinas
                $('#motivoDisciplinas').removeClass('hidden');
                buscarMultiselecaoDisciplinas();
            } else  if (motivoId == 9) {//2o. chamada
                $('#motivo9').removeClass('hidden');
                buscarDisciplinas();
                buscarProfessores();
            } else if (motivoId == 21) { //Convalidação
                $('#motivo21').removeClass('hidden');
                buscarDisciplinasItemConvalidacao();
                itemConvalidacaoDispensadoSelect2();
                formatInputsConvalidacao();
            }
            validador.resetForm();
        }
    });

    //-----[ QUANDO ESTÁ EDITANDO ]---
    if ($("#motivo").val() == 9) {
        $('#disciplina').select2();
    } else if (exibirMultiselecaoDeDisciplinas($("#motivo").val())) {
        buscarDisciplinasDoRequerimento();
    }

    function buscarDisciplinasDoRequerimento() {
        $.getJSON('/ProjetoJSP/disciplina/findByDisciplinasFromRequerimento', getRequerimentoId(), function (data) {
            $.each(data, function (index) {
                var disciplina = data[index];
                disciplinasSelect.append($('<option selected>').text(disciplina.codigo + ' - ' + disciplina.nome).val(disciplina.id));
            });
            disciplinasSelect.bootstrapDualListbox('refresh', true);
        });
    }

    function buscarMultiselecaoDisciplinas() {
        $.getJSON('/ProjetoJSP/disciplina/findByCurso', getCursoId(), function (data) {
            disciplinasSelect.empty();
            $.each(data, function (index) {
                var disciplina = data[index];
                disciplinasSelect.append($('<option>').text(disciplina.codigo + ' - ' + disciplina.nome).val(disciplina.id));
            });
            disciplinasSelect.bootstrapDualListbox('refresh');
        });
    }

    function buscarDisciplinas() {
        $.getJSON('/ProjetoJSP/disciplina/findByCurso', getCursoId(), function (data) {
            selector = $('#disciplina');
            selector.empty();
            $.each(data, function (index) {
                var disciplina = data[index];
                selector.append($('<option>').text(disciplina.codigo + ' - ' + disciplina.nome).val(disciplina.id));
            });
        });
        $('#disciplina').select2();
    }

    function buscarProfessores() {
        $.getJSON('/ProjetoJSP/usuario/findByProfessores', {}, function (data) {
            selector = $('#professor');
            selector.empty();
            $.each(data, function (index) {
                var professor = data[index];
                selector.append($('<option>').text(professor.nome).val(professor.id));
            });
        });
        $('#professor').select2();
    }
});

function getRequerimentoId() {
    return {"requerimentoId": $("#id").val()};
}

function getCursoId() {
    return {"curso": $("#curso").val()};
}

function exibirMultiselecaoDeDisciplinas(motivoId) {
    //Cancelamento das disciplinas || Matrícula nas disciplinas || Planos de Ensino/Ementas das disciplinas
    return (motivoId == 5 || motivoId == 15 || motivoId == 17);
}

function deleteAnexo(id) {
    swal({
        title: 'Confirma a exclusão do anexo?!',
        text: "Esta ação não poderá ser desfeita!",
        type: 'question', //warning
        showLoaderOnConfirm: false,
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sim, excluir!!',
        cancelButtonText: 'Não',
        allowOutsideClick: false,
    }).then(function () {
        var url = '/ProjetoJSP/requerimento/anexo/delete/' + id;
        $.ajax({
            type : 'DELETE',
            url : url,
            success : function(data) {
                data = JSON.parse(data);
                if (data.state == "OK"){
                    swal("Removido!",
                        data.message,
                        "success").then(function () {
                        $('#anexo' + id).remove();
                        if ($("#requerimentoAnexos > .attached .dz-preview").length == 0) {
                            $('#requerimentoAnexos').remove();
                        }
                    }).catch(swal.noop); // esse catch evita erro no console do browser;
                } else {
                    swal("Falhou!", data.message, "error").catch(swal.noop); // esse catch evita erro no console do browser;
                }
            },//Fim success
            error : function() {
                swal("Erro!", "Falha ao remover registro.", "error").catch(swal.noop); // esse catch evita erro no console do browser;
            }
        }); //Fim ajax
    }).catch(swal.noop); // esse catch evita erro no console do browser;
}

function buscarDisciplinasItemConvalidacao() {
    $.getJSON('/ProjetoJSP/disciplina/findByCurso', getCursoId(), function (data) {
        var length = $("#disciplinasConvalidacao").find("tbody").find(".itemConvalidacao").length-1;
        selector = $($("#disciplinasConvalidacao").find("tbody").find(".itemConvalidacao")[length]).find(".disciplinaUtfpr").find("select");
        selector.empty();
        $.each(data, function (index) {
            var disciplina = data[index];
            selector.append($('<option>').text(disciplina.codigo + ' - ' + disciplina.nome).val(disciplina.id));
        });
        selector.select2();
    });
}

function buscarDisciplinasItemsConvalidacao() {
    $.getJSON('/ProjetoJSP/disciplina/findByCurso', getCursoId(), function (data) {
        $(".itemConvalidacao").each(function (index, element) {
            selector = $(element).find(".disciplinaUtfpr").find("select");
            selector.empty();
            $.each(data, function (index) {
                var disciplina = data[index];
                selector.append($('<option>').text(disciplina.codigo + ' - ' + disciplina.nome).val(disciplina.id));
            });
            selector.select2();
        });
    });
}

function itemConvalidacaoDispensadoSelect2() {
    var length = $("#disciplinasConvalidacao").find("tbody").find(".itemConvalidacao").length-1;
    select = $($("#disciplinasConvalidacao").find("tbody").find(".itemConvalidacao")[length]).find(".dispensado").find("select");
    select.select2();
}

function getUniqueId() {
    return new Date().getTime();
}

function adicionarItemConvalidacao() {
    if ($('#frm').valid()) {
        var id = getUniqueId();
        var disciplinaConvalidacao = "disciplinaConvalidacao" + id;
        var cargaHoraria = "cargaHoraria" + id;
        var nota = "nota" + id;
        var frequencia = "frequencia" + id;
        var notaFinal = "notaFinal" + id;
        var freqFinal = "freqFinal" + id;

        $("#disciplinasConvalidacao").find("tbody")
            .append('<tr class="itemConvalidacao">' +
                '<td class="disciplinaUtfpr"><select name="disciplinaUtfpr" class="form-control"></select></td>' +
                '<td class="disciplinaConvalidacao"><input name="'+disciplinaConvalidacao+'" type="text" class="form-control" /></td>' +
                '<td class="cargaHoraria"><input name="'+cargaHoraria+'" type="text" class="form-control" /></td>' +
                '<td class="nota"><input name="'+nota+'" type="text" class="form-control" /></td>' +
                '<td class="frequencia"><input name="'+frequencia+'" type="text" class="form-control" /></td>' +
                '<td class="notaFinal"><input name="'+notaFinal+'" type="text" class="form-control" /></td>' +
                '<td class="freqFinal"><input name="'+freqFinal+'" type="text" class="form-control" /></td>' +
                '<td class="dispensado">' +
                '<select class="form-control">' +
                '<option value="true">Sim</option><option value="false">Não</option>' +
                '</select>' +
                '</td>' +
                '<td>' +
                '<div class="acoes">' +
                '<div title="Adicionar" onclick="adicionarItemConvalidacao();" class="ui-pg-div ui-inline-edit" style="float: left;cursor: pointer;">' +
                '<span class="fa fa-plus"></span>' +
                '</div>' +
                '<div title="Excluir" onclick="excluirItemConvalidacao(this);" class="ui-pg-div ui-inline-del" style="float: left;cursor: pointer;">' +
                '<span class="fa fa-trash"></span>' +
                '</div>' +
                '</div>' +
                '</td>');
        buscarDisciplinasItemConvalidacao();
        itemConvalidacaoDispensadoSelect2();
        formatInputsConvalidacao();
        addRulesOnLastItemConvalidacao();

        if ($(".itemConvalidacao").length == 2) {
            $(".acoes").first()
                .append('<div title="Excluir" onclick="excluirItemConvalidacao(this);" class="ui-pg-div ui-inline-del" style="float: left;cursor: pointer;">' +
                '<span class="fa fa-trash"></span></div>');
        }
    }
}

function addRulesOnLastItemConvalidacao() {
    $itemConvalidacao = $("#disciplinasConvalidacao").find("tbody").find(".itemConvalidacao").last();
    $itemConvalidacao.find('.disciplinaConvalidacao').find('input').rules("add", {
        required: true,
        maxlength: 100
    });
    $itemConvalidacao.find('.cargaHoraria').find('input').rules("add", {
        required: true,
        maxlength: 3,//sempre será número inteiro positivo
        number: true,
        intNumber: true
    });
    $itemConvalidacao.find('.nota').find('input').rules("add", {
        required: true,
        number: true,
        max: 10.00
    });
    $itemConvalidacao.find('.frequencia').find('input').rules("add", {
        required: true,
        number: true,
        max: 100.00
    });
    $itemConvalidacao.find('.notaFinal').find('input').rules("add", {
        required: true,
        number: true,
        max: 10.00
    });
    $itemConvalidacao.find('.freqFinal').find('input').rules("add", {
        required: true,
        number: true,
        max: 100.00
    });
}

function formatInputsConvalidacao() {
    formatCargaHoraria();
    formatNota();
    formatFrequencia();
}

function formatCargaHoraria() {
    $(".cargaHoraria").last().find('input').limitRegex(/^[0-9]{0,3}$/);
}

function formatNota() {
    $(".nota").last().find('input').limitRegex(/^[0-9]{0,2}\.?[0-9]{0,2}$/);
    $(".notaFinal").last().find('input').limitRegex(/^[0-9]{0,3}\.?[0-9]{0,2}$/);
}

function formatFrequencia() {
    $(".frequencia").last().find('input').limitRegex(/^[0-9]{0,3}\.?[0-9]{0,2}$/);
    $(".freqFinal").last().find('input').limitRegex(/^[0-9]{0,3}\.?[0-9]{0,2}$/);
}

function excluirItemConvalidacao(element) {
    //se estou excluindo um item, e ainda existem dois, a opcao de exclusao do último que sobrar é removida
    var removeDel = ($(".itemConvalidacao").length == 2);

    $(element).closest(".itemConvalidacao").remove();

    if (removeDel) {
        $(".acoes").first().find(".ui-inline-del").remove();
    }
}