package br.edu.utfpr.pb.projetojsp.web.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edson on 10/10/2017.
 */
public class RequerimentoControllerUtil {
    public static List<RequerimentoStatus> getStatuses() {
        List<RequerimentoStatus> list = new ArrayList<>();
        if (ControllersUtil.hasLoggedUserRole("ROLE_DERAC")) {
            list.add(new RequerimentoStatus(0, "Aguardando DERAC"));
            list.add(new RequerimentoStatus(1, "Aprovado"));
            list.add(new RequerimentoStatus(2, "Falta de Documentos"));
            list.add(new RequerimentoStatus(3, "Recusado"));
            list.add(new RequerimentoStatus(4, "Enviar Coordenação"));
            list.add(new RequerimentoStatus(6, "Finalizar"));
        } else {
            list.add(new RequerimentoStatus(4, "Aguardando Coordenação"));
            list.add(new RequerimentoStatus(5, "Aprovado"));
            list.add(new RequerimentoStatus(3, "Recusado"));
        }
        return list;
    }
}
