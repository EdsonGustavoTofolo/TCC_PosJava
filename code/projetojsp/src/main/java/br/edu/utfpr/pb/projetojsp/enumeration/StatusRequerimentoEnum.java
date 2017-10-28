package br.edu.utfpr.pb.projetojsp.enumeration;

/**
 * Created by Edson on 04/07/2017.
 */
public enum StatusRequerimentoEnum implements EnumLabel {
    //Primeiro status após o usuário gravar o requerimento
    EM_ABERTO("Em Aberto"),
    //Caso o DERAC aprove um Requerimento, sem a necessidade de delegar o requerimento para outro departamento
    APROVADO("Aprovar"),
    //Quando o DERAC passa para algum outro departamento e o mesmo devolve para o DERAC
    DEVOLVIDO_DERAC("Devolver DERAC"),
    //Quando o requerimento é cancelado
    CANCELADO("Cancelar"),
    //Quando o DERAC encaminha o requerimento para a Coordenação, geralmente quando é "2a. chamada de prova" e/ou "Convalidação"
    AGUARDANDO_COORDENACAO("Encaminhar Coordenação"),
    AGUARDANDO_DIRGRAD("Encaminhar DIRGRAD"),
    AGUARDANDO_NUAPNE("Encaminhar NUAPNE"),
    AGUARDANDO_DIRGE("Encaminhar DIRGE"),
    //Último status onde somente o DERAC poderá alterar
    FINALIZADO("Finalizar");

    private String label;

    StatusRequerimentoEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
