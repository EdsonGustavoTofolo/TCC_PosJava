package br.edu.utfpr.pb.projetojsp.enumeration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Edson on 14/07/2017.
 */
public class MotivoRequerimentoConsts {

    private static List<MotivoRequerimento> motivoRequerimentoList;

    public static final int AFASTAMENTO_ESTUDOS_EXTERIOR = 1;
    public static final int ATIVIDADES_DOMICILIARES = 2;
    public static final int AVALIACAO_ANTECIPADA = 3;
    public static final int AVALIACAO_DIFERENCIADA = 4;
    public static final int CANCELAMENTO_DISCIPLINAS = 5;
    public static final int DECLARACAO_MATRICULA = 6;
    public static final int DECLARACAO_PROVAVEL_FORMANDO = 7;
    public static final int DESISTENCIA_CURSO = 8;
    public static final int SEGUNDA_CHAMADA_PROVA = 9;
    public static final int DIPLOMA_SEGUNDA_VIA = 10;
    public static final int HISTORICO_ATUALIZADO = 11;
    public static final int HISTORICO_CONCLUSAO_ENSINO_SUPERIOR = 12;
    public static final int MATRICULA_ATIVIDADES_COMPLEMENTARES = 13;
    public static final int MATRICULA_ESTAGIO_SUPERVISIONADO = 14;
    public static final int MATRICULA_NAS_DISCIPLINAS = 15;
    public static final int MUDANCA_TURMAS = 16;
    public static final int PLANOS_ENSINO_EMENTAS = 17;
    public static final int TRANCAMENTO_TOTAL_MATRICULA = 18;
    public static final int SEGUNDA_VIA_CRACHA = 19;
    public static final int OUTROS = 20;
    public static final int CONVALIDACAO = 21;

    public static List<MotivoRequerimento> getMotivosList() {
        if (Objects.isNull(motivoRequerimentoList)) {
            motivoRequerimentoList = new ArrayList<>();
            motivoRequerimentoList.add(new MotivoRequerimento(1, "Afastamento para estudos no exterior"));
            motivoRequerimentoList.add(new MotivoRequerimento(2, "Atividades Domiciliares"));
            motivoRequerimentoList.add(new MotivoRequerimento(3, "Avaliação Antecipada"));
            motivoRequerimentoList.add(new MotivoRequerimento(4, "Avaliação Diferenciada"));
            motivoRequerimentoList.add(new MotivoRequerimento(5, "Cancelamento de disciplinas"));
            motivoRequerimentoList.add(new MotivoRequerimento(6, "Declaração de Matrícula"));
            motivoRequerimentoList.add(new MotivoRequerimento(7, "Declaração de Provável Formando"));
            motivoRequerimentoList.add(new MotivoRequerimento(8, "Desistência do Curso"));
            motivoRequerimentoList.add(new MotivoRequerimento(9, "2ª chamada de prova"));
            motivoRequerimentoList.add(new MotivoRequerimento(10, "Diploma (2ª via)"));
            motivoRequerimentoList.add(new MotivoRequerimento(11, "Histórico Atualizado"));
            motivoRequerimentoList.add(new MotivoRequerimento(12, "Histórico de Conclusão do Ensino Superior"));
            motivoRequerimentoList.add(new MotivoRequerimento(13, "Matrícula em Atividades Complementares"));
            motivoRequerimentoList.add(new MotivoRequerimento(14, "Matrícula em Estágio Supervisionado"));
            motivoRequerimentoList.add(new MotivoRequerimento(15, "Matrícula nas disciplinas"));
            motivoRequerimentoList.add(new MotivoRequerimento(16, "Mudança para as turmas"));
            motivoRequerimentoList.add(new MotivoRequerimento(17, "Planos de Ensino/Ementas"));
            motivoRequerimentoList.add(new MotivoRequerimento(18, "Trancamento Total da Matrícula"));
            motivoRequerimentoList.add(new MotivoRequerimento(19, "2ª via do crachá"));
            motivoRequerimentoList.add(new MotivoRequerimento(20, "Outros"));
            motivoRequerimentoList.add(new MotivoRequerimento(21, "Convalidação"));
        }
        return motivoRequerimentoList;
    }
}

