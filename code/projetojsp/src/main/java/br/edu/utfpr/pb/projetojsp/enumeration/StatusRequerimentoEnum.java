package br.edu.utfpr.pb.projetojsp.enumeration;

/**
 * Created by Edson on 04/07/2017.
 */
public enum StatusRequerimentoEnum implements EnumLabel {
    EM_ANDAMENTO("Em Andamento"),
    AGUARDANDO_COORDENACAO("Aguardando Coordenação"),
    AGUARDANDO_DERAC("Aguardando Avaliação DERAC"),
    FALTA_DOC("Falta Documento"),
    CONCLUIDO("Concluído");

    private String label;

    StatusRequerimentoEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
