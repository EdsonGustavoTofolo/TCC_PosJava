package br.edu.utfpr.pb.projetojsp.enumeration;

/**
 * Created by Edson on 04/07/2017.
 */
public enum StatusRequerimentoEnum implements EnumLabel {
    AGUARDANDO_DERAC("Aguardando Avaliação DERAC"),
    APROVADO_DERAC("Aprovado"),
    FALTA_DOCUMENTOS("Falta de Documentos"),
    RECUSADO("Recusado"),
    AGUARDANDO_COORDENACAO("Aguardando Coordenação"),
    APROVADO_COORDENACAO("Aprovado"),
    FINALIZADO("Finalizado");

    private String label;

    StatusRequerimentoEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
