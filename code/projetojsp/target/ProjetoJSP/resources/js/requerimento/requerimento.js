/**
 * Created by Edson on 11/07/2017.
 */
$(document).ready(function () {
    var $input = $("#motivoTxt");
    $input.typeahead({
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
    $input.change(function() {
        var motivo = $input.typeahead("getActive");
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
    });
});

$("#frm").submit(function() {
    $.ajax({
        type : $("#frm").attr('method'),
        url : $("#frm").attr('action'),
        data : $('#frm').serialize(),
        success : function(data) {
            swal({
                title : "Salvo!",
                text : "Registro salvo com sucesso.",
                type : "success",
                showCancelButton : false,
                confirmButtonColor : "#DD6B5",
                confirmButtonText : "Ok",
                closeOnConfirm : false
            }, function() {
                //window.location = '<c:url value="/produto/"/>';
            });
        },//Fim success
        error : function() {
            swal("Falhou!", "Falha ao salvar registro.", "error");
        }
    });//Fim ajax
    return false;
}); //Fim submit
