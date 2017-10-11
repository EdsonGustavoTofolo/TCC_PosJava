package br.edu.utfpr.pb.projetojsp.enumeration;

/**
 * Created by Edson on 04/07/2017.
 */
public enum StatusRequerimentoEnum implements EnumLabel {
    AGUARDANDO_DERAC("Enviar ao DERAC"),
    APROVADO_DERAC("Aprovado DERAC"),
    FALTA_DOCUMENTOS("Falta de Documentos"),
    RECUSADO("Recusado"),
    AGUARDANDO_COORDENACAO("Enviar Coordenação"),
    APROVADO_COORDENACAO("Aprovado Coordenação"),
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
