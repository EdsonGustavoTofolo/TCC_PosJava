/**
 * Created by Edson on 28/10/2017.
 */
function visualizarRequerimento(requerimentoId, element) {
    $.getJSON('/ProjetoJSP/requerimento/findById', {"id": requerimentoId}, function (requerimento) {
        var dataRequerimento = new Date(requerimento.data);
        var momentData = moment(dataRequerimento).locale('pt-br');

        var dataRequerimentoAgo = momentData.fromNow(); //quanto tempo atrás
        dataRequerimento = momentData.format('llll');

        var requerimentoHtml =
        '<div aria-hidden="true" aria-labelledby="requerimentoDialog" role="dialog" tabindex="-1" id="requerimentoViewer" class="modal fade">' +
            '<div class="modal-dialog">' +
                '<div class="modal-content">' +
                    '<div class="modal-header">' +
                        '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                        '<h4 class="modal-title">Requerimento <small id="requerimentoId">'+requerimento.id+'</small> ' +
                        '<small>/</small> ' +
                        '<small id="requerimentoData" data-toggle="tooltip" data-placement="right" data-original-title="'+dataRequerimentoAgo+'">'+dataRequerimento+'</small></h4>' +
                    '</div>' +
                '<div class="modal-body">' +
                    '<label for="alunoNome">Aluno:</label>' +
                    '<input id="alunoNome" type="text" value="'+requerimento.usuario.nome+'" class="form-control" disabled/><br/>' +
                    '<label for="motivo">Motivo do Requerimento:</label>' +
                    '<input id="motivo" type="text" value="'+motivoList[requerimento.motivo - 1].descricao+'" class="form-control" disabled/><br/>' +
                    '<label for="curso">Curso:</label>';

        if (requerimento.motivo == 9) { //2 chamada de prova
            requerimentoHtml +=
                        '<input id="curso" type="text" value="'+requerimento.disciplinas[0].disciplina.curso.usuario.nome+'" class="form-control" disabled/>' +
                        '<div id="motivo9"><br/>' +
                            '<label for="disciplina">Disciplina:</label>' +
                            '<input id="disciplina" type="text" value="'+requerimento.disciplinas[0].disciplina.codigo + " - " + requerimento.disciplinas[0].disciplina.nome+'" class="form-control" disabled/><br/>' +
                            '<label for="professor">Professor:</label>' +
                            '<input id="professor" type="text" value="'+requerimento.disciplinas[0].professor.nome+'" class="form-control" disabled/><br/>' +
                            '<label for="data">Data:</label>' +
                            '<input id="data" type="date" pattern="dd/MM/yyyy" value="'+requerimento.disciplinas[0].dataProva+'" class="form-control" disabled/>' +
                        '</div><br/>';
        } else if (requerimento.motivo == 5 || requerimento.motivo == 15 || requerimento.motivo == 17) { //exibir tabela com disciplinas
                requerimentoHtml += '<input id="curso" type="text" value="'+requerimento.disciplinas[0].disciplina.curso.usuario.nome+'" class="form-control" disabled/>';
                requerimentoHtml +=
                '<div id="disciplinas"><br/>' +
                    '<label>Disciplinas:</label><div id="disciplinaList"><table id="jqGridDisciplinas"></table><div id="jqGridDisciplinasPager"></div></div>' +
                '</div>';
        } else {
            requerimentoHtml += '<input id="curso" type="text" value="'+requerimento.usuario.curso.usuario.nome+'" class="form-control" disabled/><br/>';
        }

        if (requerimento.anexos.length > 0) { //exibir anexos para efetuar downloads
            requerimentoHtml +=
                '<div id="anexos"><br/><label>Anexos:</label><div class="attached">';

            requerimento.anexos.forEach(function (anexo) {
                requerimentoHtml +=
                    '<div id="anexo' + anexo.id + '" class="dz-preview dz-file-preview">' +
                        '<div class="dz-image"><img data-dz-thumbnail=""></div>' +
                        '<div class="dz-details">'+
                        '<div class="dz-size">'+
                        '<span><strong>' + anexo.size + '</strong> ' + anexo.unitSize + '</span>'+
                        '</div>'+
                        '<div class="dz-filename">'+
                        '<span>' + anexo.nome + '</span>'+
                        '</div>'+
                        '</div>'+
                        '<div class="dz-download" style="top: 80%;">'+
                        '<a href="/ProjetoJSP/requerimento/anexos/download/' + anexo.id + '">Download</a>'+
                        '</div>'+
                        '</div>'
            });

            requerimentoHtml += '</div></div><br/>';
        }

        requerimentoHtml += '<label for="observacao">Observações:</label>' +
        '<textarea id="observacao" name="observacao" class="form-control" rows="5" disabled>'+requerimento.observacao+'</textarea>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';

        $(element).html('')
            .append('<a id="linkOpenModalReq" class="hidden" data-toggle="modal" href="index.jsp#requerimentoViewer"></a>')
            .append(requerimentoHtml);

        if (requerimento.motivo == 5 || requerimento.motivo == 15 || requerimento.motivo == 17) {
            $("#jqGridDisciplinas").jqGrid({
                datatype: "jsonstring",
                datastr: JSON.stringify(requerimento.disciplinas),
                jsonReader: {repeatitems: false},
                colModel: [
                    {label: "Código", name: "codigo", jsonmap: 'disciplina.codigo', width: 100},
                    {label: "Nome", name: "nome", jsonmap: 'disciplina.nome', width: 400}
                ],
                pager: '#jqGridDisciplinasPager',
                rowNum: 5,
                width: 'auto',
                heigth: '200',
                scroll: 1,
                viewrecords: true
            });
        }

        $('#requerimentoViewer').tooltip({
            selector: "[data-toggle=tooltip]",
            container: "body"
        });

        $("#linkOpenModalReq").click();
    });
}