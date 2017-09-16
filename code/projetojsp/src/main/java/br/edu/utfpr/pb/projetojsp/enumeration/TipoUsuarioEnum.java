package br.edu.utfpr.pb.projetojsp.enumeration;

/**
 * Created by Edson on 16/09/2017.
 */
public enum TipoUsuarioEnum implements EnumLabel {
    ALUNO("Aluno"),
    PROFESSOR("Professor"),
    DERAC("DERAC"),
    COORDENACAO("Coordenação");

    private String label;

    TipoUsuarioEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
