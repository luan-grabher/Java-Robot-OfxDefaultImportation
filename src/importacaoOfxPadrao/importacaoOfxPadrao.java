package importacaoOfxPadrao;

import Auxiliar.Valor;
import Entity.Executavel;
import LctoTemplate.CfgBancoTemplate;
import Robo.AppRobo;
import TemplateContabil.Control.ControleTemplates;
import java.util.ArrayList;
import java.util.List;

public class importacaoOfxPadrao {

    private static Integer empresa = 0;
    private static String nomePastaEmpresa = "";
    private static String nomeApp = "";

    public static void main(String[] args) {
        try {
            AppRobo robo = new AppRobo(nomeApp);

            //String parametros = "[mes:04][ano:2019][codigoEmpresa:722][nomePastaEmpresa:Vinicius Dalcin][nomeApp:Vinicius Dalcin Importação][banco:Banco do Brasil#1#001|.ofx#copia]";
            //robo.definirParametros(parametros);
            robo.definirParametros();
            empresa = robo.getParametro("codigoEmpresa").getInteger();
            nomePastaEmpresa = robo.getParametro("nomePastaEmpresa").getString();
            nomeApp = robo.getParametro("nomeApp").getString();
            String pastaPrincipal = robo.getParametro("pastaPrincipal").getString();

            int mes = robo.getParametro("mes").getMes();
            int ano = robo.getParametro("ano").getInteger();

            Integer nroBanco = 0;
            String nomeBanco = "";
            String filtroArquivo = "";
            String bancoParametro = robo.getParametro("banco").getString();
            if (!"".equals(bancoParametro)) {
                String[] bancoDados = bancoParametro.split("#", 3);
                nomeBanco = bancoDados[0];
                nroBanco = (new Valor(bancoDados[1])).getInteger();
                filtroArquivo = bancoDados[2].replaceAll("\\|", ";");
            }

            robo.setNome(nomeApp + nomeBanco);
            robo.executar(
                    principal(
                            mes,
                            ano,
                            nroBanco,
                            nomeBanco,
                            filtroArquivo,
                            pastaPrincipal
                    )
            );
        } catch (Exception e) {
            System.out.println("Ocorreu um erro na aplicação: " + e);
            System.exit(0);
        }
    }

    public static String principal(int mes, int ano, Integer nroBanco, String banco, String filtroArquivo, String pastaPrincipal) {
        try {

            ControleTemplates controle = new ControleTemplates(mes, ano, empresa, nomePastaEmpresa,pastaPrincipal);
            controle.definirFilesAndPaths();

            CfgBancoTemplate cfgBanco = new CfgBancoTemplate();
            cfgBanco.setContaBanco(nroBanco);
            cfgBanco.setEmpresa(empresa);
            cfgBanco.setFiltroNomeArquivoOriginal(filtroArquivo);
            cfgBanco.setNomeBanco(banco);

            List<Executavel> executaveis = new ArrayList<>();
            executaveis.add(controle.new definirFileTemplatePadrao());
            executaveis.add(controle.new importacaoPadraoBanco(cfgBanco));

            return AppRobo.rodarExecutaveis(nomeApp, executaveis);
        } catch (Exception e) {
            return "Ocorreu um erro no Java: " + e;
        }
    }

}
