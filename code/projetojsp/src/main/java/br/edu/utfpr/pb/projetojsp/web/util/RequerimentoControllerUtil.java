package br.edu.utfpr.pb.projetojsp.web.util;

import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edson on 10/10/2017.
 */
public class RequerimentoControllerUtil {
    public static List<RequerimentoStatus> getStatuses() {
        List<RequerimentoStatus> list = new ArrayList<>();
        if (ControllersUtil.hasLoggedUserRole("ROLE_DERAC")) {
            list.add(new RequerimentoStatus(StatusRequerimentoEnum.EM_ABERTO.ordinal(), StatusRequerimentoEnum.EM_ABERTO.getLabel()));
            list.add(new RequerimentoStatus(StatusRequerimentoEnum.APROVADO.ordinal(), StatusRequerimentoEnum.APROVADO.getLabel()));
            list.add(new RequerimentoStatus(StatusRequerimentoEnum.CANCELADO.ordinal(), StatusRequerimentoEnum.CANCELADO.getLabel()));
            list.add(new RequerimentoStatus(StatusRequerimentoEnum.AGUARDANDO_COORDENACAO.ordinal(), StatusRequerimentoEnum.AGUARDANDO_COORDENACAO.getLabel()));
            list.add(new RequerimentoStatus(StatusRequerimentoEnum.AGUARDANDO_DIRGE.ordinal(), StatusRequerimentoEnum.AGUARDANDO_DIRGE.getLabel()));
            list.add(new RequerimentoStatus(StatusRequerimentoEnum.AGUARDANDO_DIRGRAD.ordinal(), StatusRequerimentoEnum.AGUARDANDO_DIRGRAD.getLabel()));
            list.add(new RequerimentoStatus(StatusRequerimentoEnum.AGUARDANDO_NUAPNE.ordinal(), StatusRequerimentoEnum.AGUARDANDO_NUAPNE.getLabel()));
            list.add(new RequerimentoStatus(StatusRequerimentoEnum.FINALIZADO.ordinal(), StatusRequerimentoEnum.FINALIZADO.getLabel()));
        } else if (ControllersUtil.hasLoggedUserRole("ROLE_COORDENACAO")) {
            list.add(new RequerimentoStatus(StatusRequerimentoEnum.AGUARDANDO_COORDENACAO.ordinal(), StatusRequerimentoEnum.AGUARDANDO_COORDENACAO.getLabel()));
            list.add(new RequerimentoStatus(StatusRequerimentoEnum.DEVOLVIDO_DERAC.ordinal(), StatusRequerimentoEnum.DEVOLVIDO_DERAC.getLabel()));
        }
        //TODO ver da DIRGE, DIRGRAD e NUAPE
        return list;
    }
}
