/**
 * Created by Edson on 29/10/2017.
 */
function createModalParecer(element, modalId, textareaId, title, labelObs) {
    $(element).html("")
        .append(//'<a id="linkOpenModalObs" class="hidden" data-toggle="modal" href="requerimentoForm.jsp#obsViewer"></a>' +
            '<div aria-hidden="true" aria-labelledby="obsDialog" role="dialog" tabindex="-1" id="'+modalId+'" class="modal fade">' +
            '<div class="modal-dialog">' +
            '<div class="modal-content">' +
            '<div class="modal-header">' +
            '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
            '<h4 class="modal-title">' + title + '</h4>' +
            '</div>' +
            '<div class="modal-body">' +
            '<p>' +
            '<label for="deferido">Deferido:</label>' +
            '<select id="deferido" name="deferido" class="form-control">' +
            '<option value="true">Sim</option><option value="false">Não</option>' +
            '</select>' +
            '</p><p>' +
            '<label for="'+textareaId+'">' + labelObs + '</label>' +
            '<textarea id="'+textareaId+'" name="'+textareaId+'" class="form-control" rows="5"></textarea>' +
            '</p>' +
            '</div>' +
            '<div class="modal-footer">' +
            '<button data-dismiss="modal" class="btn btn-default" type="button">Cancelar</button>' +
            '<button id="confirmar'+modalId+'" class="btn btn-primary" type="button">Confirmar</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>');
}

function changeStatus(element, status, requerimentoId, successAjaxCallback, hideModalCallback) {

    createModalParecer(element, "obsViewer", "textoObs", "Alteração do Status", "Observação:");

    //$("#linkOpenModalObs").click(); // Abre o modal com o campo de Deferido e a Observação

    $("#obsViewer").modal('show');

    $("#deferido").select2();

    var ok = false;

    if (typeof(status) == "number") {
        var path = '/ProjetoJSP/requerimento/edit/' + requerimentoId + '/changeStatus/' + status;
        $("#confirmarobsViewer").click(function () {
            swal({
                title: 'Confirma a alteração do Status?',
                text: "Esta ação não poderá ser desfeita!",
                type: 'question', //warning
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sim, alterar!!',
                cancelButtonText: 'Não',
                allowOutsideClick: false
            }).then(function () {
                ok = true;
                reqAJAX(path, successAjaxCallback);
                $("#obsViewer").modal("hide");
            }).catch(swal.noop); // esse catch evita erro no console do browser
        });
    } else {
        $("#confirmarObs").click(function () {
            ok = true;
            var path = '/ProjetoJSP/requerimento/edit/' + requerimentoId + '/changeStatusKanban/' + status;
            reqAJAX(path, successAjaxCallback);
            $("#obsViewer").modal("hide");
        });
    }

    if (hideModalCallback !== undefined) {
        $('#obsViewer').on('hidden.bs.modal', function () {
            if (!ok) {
                hideModalCallback();
            }
        });
    }
}

function reqAJAX(url, successCallback) {
    var texto = $("#textoObs").val();
    var deferido = $("#deferido").select2("val");
    var requerimentoObservacao = {"texto": texto, "deferido": deferido};

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type : 'PUT',
        url : url,
        data: JSON.stringify(requerimentoObservacao),
        success : successCallback,
        error : function() {
            swal("Erro!", "Falha ao alterar status.", "error").catch(swal.noop);
        }
    }); //Fim ajax
}