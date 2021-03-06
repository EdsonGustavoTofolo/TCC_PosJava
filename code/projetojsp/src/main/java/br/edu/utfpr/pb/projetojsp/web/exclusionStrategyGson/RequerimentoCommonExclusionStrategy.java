package br.edu.utfpr.pb.projetojsp.web.exclusionStrategyGson;

import br.edu.utfpr.pb.projetojsp.model.ParecerConvalidacao;
import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import br.edu.utfpr.pb.projetojsp.model.Usuario;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by Edson on 05/10/2017.
 */
public class RequerimentoCommonExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return Usuario.class.equals(fieldAttributes.getDeclaringClass()) && fieldAttributes.getName().equals("curso") ||
                ParecerConvalidacao.class.equals(fieldAttributes.getDeclaringClass());
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return Requerimento.class.equals(aClass);
    }
}
