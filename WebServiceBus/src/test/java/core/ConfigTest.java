package core;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import core.model.Bus;
import core.model.BusRepository;
import core.model.Linha;
import core.model.LinhaRepository;
import core.model.Ponto;
import core.model.PontoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfig.class})
public class ConfigTest {

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private BusRepository busRepository;

	@Autowired
	private LinhaRepository linhaRepository;

	@Autowired
	private PontoRepository pontoRepository;

	@Before
	public void setup() {
//		prepararBaseInicial();
	}

	@After
	public void tearDown() {
//		mongoOperations.remove(new Query(), Bus.class);
//		mongoOperations.remove(new Query(), Linha.class);
//		mongoOperations.remove(new Query(), Ponto.class);
	}

	@Test
	public void testarFindAll() {
		List<Bus> todosOnibus = busRepository.findAll();
		Assert.assertFalse(todosOnibus.isEmpty());
	}
	
	@Test
	public void testarInsercaoSimple() {
		
		ArrayList<Ponto> rota = new ArrayList<Ponto>();
		Ponto ponto = new Ponto(903, 12.12, 23.23);
		ponto.setPontoDeOnibus(true);
		Ponto ponto2 = new  Ponto(905, -23.433184, -51.936641);
		ponto2.setPontoDeOnibus(true);
		rota.add(ponto);
		rota.add(ponto2);
		busRepository.save(new Bus(901, new Linha("cerro azul", 902, rota)));
		
		Assert.assertNotNull(busRepository.findById(901));
	}

	@Test
	public void testarBuscarOnibusPorLinha() {
		
		List<Ponto> rota = new ArrayList<Ponto>();
		Ponto ponto = new Ponto(903, 12.12, 23.23);
		ponto.setPontoDeOnibus(true);
		rota.add(ponto);
		busRepository.save(new Bus(901, new Linha("cerro azul", 902, rota)));

		rota = new ArrayList<Ponto>();
		ponto = new Ponto(4, 12.12, 23.23);
		ponto.setPontoDeOnibus(true);
		rota.add(ponto);
		busRepository.save(new Bus(902, new Linha("cerro azul2", 903, rota)));
		
		List<Bus> onibusLinha = busRepository.findAllByLinha(902);
		Assert.assertEquals(2, onibusLinha.size());

	}

	public void prepararBaseInicial() {
        Ponto p1 = new Ponto(1, -23.439819, -51.936084);
        Ponto p2 = new Ponto(912, -23.433184, -51.936641);
        Ponto p3 = new Ponto(3, -23.426374, -51.939787);
        Ponto p4 = new Ponto(4, -23.422396, -51.939899);
        Ponto p5 = new Ponto(5, -23.416510, -51.939465);
        Ponto p6 = new Ponto(6, -23.407427, -51.938087);
        Ponto p7 = new Ponto(7, -23.428249, -51.932561);
        Ponto p8 = new Ponto(8, -23.434942, -51.944942);
        Ponto p9 = new Ponto(14, -23.429174, -51.936809);
        Ponto p30 = new Ponto(30, -23.41064165767885, -51.93619251251221); 
        Ponto p31 = new Ponto(31, -23.41524934042091, -51.93657875061035); 
        Ponto p32 = new Ponto(32, -23.419581203488928, -51.93636417388916);
        Ponto p33 = new Ponto(33, -23.42422795343768, -51.9364070892334);
        Ponto p34 = new Ponto(34, -23.42749633229096, -51.937522888183594);
        Ponto p35 = new Ponto(35, -23.428480767922245, -51.9373083114624);
        Ponto p36 = new Ponto(36, -23.431749041656513, -51.93580627441406);
        Ponto p37 = new Ponto(37, -23.434662856133865, -51.93464756011963);
        Ponto p38 = new Ponto(38, -23.4395453201843, -51.93284511566162); 
        Ponto p40 = new Ponto(40, -23.442301470236988, -51.932759284973145);
        Ponto p41 = new Ponto(41, -23.442380216536655, -51.93417549133301); 
        
        p1.setPontoDeOnibus(true);
        p2.setPontoDeOnibus(true);
        p3.setPontoDeOnibus(true);
        p4.setPontoDeOnibus(true);
        p5.setPontoDeOnibus(true);
        p6.setPontoDeOnibus(true);
        p7.setPontoDeOnibus(true);
        p8.setPontoDeOnibus(true);
        p9.setPontoDeOnibus(true);
        p30.setPontoDeOnibus(true);
        p31.setPontoDeOnibus(true);
        p32.setPontoDeOnibus(true);
        p33.setPontoDeOnibus(true);
        p35.setPontoDeOnibus(true);
        p36.setPontoDeOnibus(true);
        p37.setPontoDeOnibus(true);
        p38.setPontoDeOnibus(true);
        p41.setPontoDeOnibus(true);

        
        ArrayList<Ponto> listaPontos = new ArrayList<Ponto>();
        listaPontos.add(p1);
        listaPontos.add(p2);
        listaPontos.add(p3);
        listaPontos.add(p4);
        listaPontos.add(p5);
        listaPontos.add(p6);
        listaPontos.add(p30);
        listaPontos.add(p32);
        listaPontos.add(p33);
        listaPontos.add(p34);
        listaPontos.add(p35);
        listaPontos.add(p36);
        listaPontos.add(p37);
        listaPontos.add(p38);
        listaPontos.add(p40);
        listaPontos.add(p41);
        
        ArrayList<Ponto> listaPontosAABB = new ArrayList<Ponto>();
        
        Ponto p52 = new Ponto(52, -23.419029883339913, -51.939239501953125); 
        listaPontosAABB.add(p52); 
        p52.setPontoDeOnibus(true); 
        Ponto p53 = new Ponto(53, -23.418911743009037, -51.942758560180664); 
        listaPontosAABB.add(p53); 
        p53.setPontoDeOnibus(true); 
        Ponto p54 = new Ponto(54, -23.41899050324134, -51.946706771850586); 
        listaPontosAABB.add(p54); 
        Ponto p56 = new Ponto(56, -23.42013252134013, -51.946964263916016); 
        listaPontosAABB.add(p56); 
        p56.setPontoDeOnibus(true); 
        Ponto p57 = new Ponto(57, -23.422140874079865, -51.94735050201416); 
        listaPontosAABB.add(p57); 
        p57.setPontoDeOnibus(true); 
        Ponto p58 = new Ponto(58, -23.424109817749635, -51.94803714752197); 
        listaPontosAABB.add(p58); 
        p58.setPontoDeOnibus(true); 
        Ponto p60 = new Ponto(60, -23.424976143677995, -51.948509216308594); 
        p60.setPontoDeOnibus(true); 
        Ponto p61 = new Ponto(61, -23.4259605980714, -51.949753761291504); 
        listaPontosAABB.add(p61); 
        p61.setPontoDeOnibus(true); 
        Ponto p62 = new Ponto(62, -23.426511889330516, -51.95155620574951); 
        listaPontosAABB.add(p62); 
        p62.setPontoDeOnibus(true); 
        Ponto p63 = new Ponto(63, -23.427338821909796, -51.95417404174805); 
        listaPontosAABB.add(p63); 
        p63.setPontoDeOnibus(true); 
        Ponto p64 = new Ponto(64, -23.428244504039164, -51.95765018463135); 
        listaPontosAABB.add(p64); 
        p64.setPontoDeOnibus(true); 
        Ponto p65 = new Ponto(65, -23.428362636033487, -51.96091175079346); 
        listaPontosAABB.add(p65); 
        p65.setPontoDeOnibus(true); 
        Ponto p66 = new Ponto(66, -23.42997709604455, -51.9598388671875); 
        listaPontosAABB.add(p66); 
        p66.setPontoDeOnibus(true); 
        Ponto p67 = new Ponto(67, -23.431237148696923, -51.95889472961426); 
        listaPontosAABB.add(p67); 
        p67.setPontoDeOnibus(true); 
        Ponto p68 = new Ponto(68, -23.43403284763503, -51.957693099975586); 
        listaPontosAABB.add(p68); 
        p68.setPontoDeOnibus(true); 
        Ponto p69 = new Ponto(69, -23.435489737731725, -51.95704936981201); 
        listaPontosAABB.add(p69); 
        p69.setPontoDeOnibus(true); 
        Ponto p70 = new Ponto(70, -23.435804738884546, -51.96112632751465); 
        listaPontosAABB.add(p70); 
        p70.setPontoDeOnibus(true); 
        Ponto p71 = new Ponto(71, -23.432890949577686, -51.966490745544434); 
        listaPontosAABB.add(p71); 
        p71.setPontoDeOnibus(true); 
        Ponto p72 = new Ponto(72, -23.431473407232257, -51.97073936462402); 
        listaPontosAABB.add(p72); 
        p72.setPontoDeOnibus(true); 
        Ponto p73 = new Ponto(73, -23.430252733588432, -51.9734001159668); 
        listaPontosAABB.add(p73); 
        Ponto p74 = new Ponto(74, -23.4309615132054, -51.97352886199951); 
        listaPontosAABB.add(p74);  
        Ponto p75 = new Ponto(75, -23.431985299277045, -51.96953773498535); 
        listaPontosAABB.add(p75); 
        p75.setPontoDeOnibus(true); 
        Ponto p76 = new Ponto(76, -23.435922864123334, -51.9598388671875); 
        listaPontosAABB.add(p76); 
        p76.setPontoDeOnibus(true); 
        Ponto p77 = new Ponto(77, -23.435489737731725, -51.95614814758301); 
        listaPontosAABB.add(p77); 
        p77.setPontoDeOnibus(true); 
        Ponto p78 = new Ponto(78, -23.43651348873366, -51.95189952850342); 
        listaPontosAABB.add(p78); 
        Ponto p79 = new Ponto(79, -23.434347852259783, -51.951212882995605); 
        listaPontosAABB.add(p79); 
        Ponto p80 = new Ponto(80, -23.432575941481165, -51.954002380371094); 
        listaPontosAABB.add(p80); 
        p80.setPontoDeOnibus(true); 
        Ponto p81 = new Ponto(81, -23.42950457320382, -51.95906639099121); 
        listaPontosAABB.add(p81);  
        Ponto p82 = new Ponto(82, -23.428480767922245, -51.958250999450684); 
        p82.setPontoDeOnibus(true); 
        Ponto p83 = new Ponto(83, -23.42769322000355, -51.955718994140625); 
        listaPontosAABB.add(p83); 
        p83.setPontoDeOnibus(true); 
        Ponto p84 = new Ponto(84, -23.42706317829129, -51.953186988830566); 
        listaPontosAABB.add(p84); 
        Ponto p85 = new Ponto(85, -23.426314999858963, -51.9508695602417); 
        listaPontosAABB.add(p85); 
        Ponto p86 = new Ponto(86, -23.425881841989632, -51.94893836975098); 
        listaPontosAABB.add(p86); 
        Ponto p87 = new Ponto(87, -23.42493676534982, -51.94812297821045); 
        listaPontosAABB.add(p87); 
        p87.setPontoDeOnibus(true); 
        Ponto p88 = new Ponto(88, -23.422810318216413, -51.94743633270264); 
        listaPontosAABB.add(p88); 
        p88.setPontoDeOnibus(true); 
        Ponto p89 = new Ponto(89, -23.421235150148902, -51.94653511047363); 
        listaPontosAABB.add(p89); 
        Ponto p90 = new Ponto(90, -23.419659963322655, -51.94636344909668); 
        listaPontosAABB.add(p90); 
        p90.setPontoDeOnibus(true); 
        Ponto p91 = new Ponto(91, -23.419856862701753, -51.9439172744751); 
        listaPontosAABB.add(p91); 
        p91.setPontoDeOnibus(true); 
        Ponto p92 = new Ponto(92, -23.41989624254241, -51.94091320037842); 
        listaPontosAABB.add(p92); 
        p92.setPontoDeOnibus(true); 
        Ponto p94 = new Ponto(94, -23.419935622371337, -51.93769454956055); 
        listaPontosAABB.add(p94); 
        Ponto p95 = new Ponto(95, -23.419029883339913, -51.93769454956055); 
        listaPontosAABB.add(p95); 
        
        ArrayList<Ponto> listaPontosCerroAzul = new ArrayList<Ponto>();
        
        Ponto p151 = new Ponto(151, -23.418523, -51.938544); 
        listaPontosCerroAzul.add(p151);
        p151.setPontoDeOnibus(true);
        Ponto p7254 = new Ponto(7254, -23.419498, -51.937681); 
        listaPontosCerroAzul.add(p7254);        
        Ponto p152 = new Ponto(152, -23.419975002188547, -51.93705081939697);
        listaPontosCerroAzul.add(p152);
        p152.setPontoDeOnibus(true);
        Ponto p153 = new Ponto(153, -23.421589564599653, -51.9364070892334);
        listaPontosCerroAzul.add(p153);
        p53.setPontoDeOnibus(true);
        Ponto p154 = new Ponto(154, -23.42505490029916, -51.9364070892334);
        listaPontosCerroAzul.add(p154);
        p154.setPontoDeOnibus(true);
        Ponto p155 = new Ponto(155, -23.428047617147445, -51.93756580352783);
        listaPontosCerroAzul.add(p155);
        p155.setPontoDeOnibus(true);
        Ponto p156 = new Ponto(156, -23.431749041656513, -51.935977935791016);
        listaPontosCerroAzul.add(p156);
        p156.setPontoDeOnibus(true);
        Ponto p157 = new Ponto(157, -23.435804738884546, -51.934261322021484);
        listaPontosCerroAzul.add(p157);
        p157.setPontoDeOnibus(true);
        Ponto p158 = new Ponto(158, -23.437340358754135, -51.93344593048096);
        listaPontosCerroAzul.add(p158);
        p158.setPontoDeOnibus(true);
        Ponto p159 = new Ponto(159, -23.439584694161237, -51.93310260772705);
        listaPontosCerroAzul.add(p159);
        p159.setPontoDeOnibus(true);
        Ponto p160 = new Ponto(160, -23.441238390598414, -51.93305969238281);
        listaPontosCerroAzul.add(p160);
        p160.setPontoDeOnibus(true);
        Ponto p161 = new Ponto(161, -23.442655828215898, -51.93284511566162);
        listaPontosCerroAzul.add(p161);
        p161.setPontoDeOnibus(true);
        Ponto p162 = new Ponto(162, -23.443679523705566, -51.93511962890625);
        listaPontosCerroAzul.add(p162);
        p162.setPontoDeOnibus(true);
        Ponto p163 = new Ponto(163, -23.444073250628158, -51.93769454956055);
        listaPontosCerroAzul.add(p163);
        Ponto p164 = new Ponto(164, -23.44572689089049, -51.93765163421631);
        listaPontosCerroAzul.add(p164);
        p164.setPontoDeOnibus(true);
        Ponto p165 = new Ponto(165, -23.447183652068208, -51.937265396118164);
        listaPontosCerroAzul.add(p165);
        Ponto p166 = new Ponto(166, -23.446868678039564, -51.933531761169434);
        listaPontosCerroAzul.add(p166);
        p166.setPontoDeOnibus(true);
        Ponto p167 = new Ponto(167, -23.448364797987086, -51.93331718444824);
        listaPontosCerroAzul.add(p167);
        p167.setPontoDeOnibus(true);
        Ponto p168 = new Ponto(168, -23.449427820284278, -51.93318843841553);
        listaPontosCerroAzul.add(p168);
        Ponto p169 = new Ponto(169, -23.448679768448457, -51.93220138549805);
        listaPontosCerroAzul.add(p169);
        p169.setPontoDeOnibus(true);
        Ponto p170 = new Ponto(170, -23.446238727729373, -51.93254470825195);
        listaPontosCerroAzul.add(p170);
        Ponto p171 = new Ponto(171, -23.446868678039564, -51.93117141723633);
        listaPontosCerroAzul.add(p171);
        p171.setPontoDeOnibus(true);
        Ponto p172 = new Ponto(172, -23.44765611170306, -51.93031311035156);
        listaPontosCerroAzul.add(p172);
        Ponto p173 = new Ponto(173, -23.446356843641336, -51.92941188812256);
        listaPontosCerroAzul.add(p173);
        p173.setPontoDeOnibus(true);
        Ponto p174 = new Ponto(174, -23.445293796631844, -51.92881107330322);
        listaPontosCerroAzul.add(p174);
        Ponto p175 = new Ponto(175, -23.445845007260086, -51.927480697631836);
        listaPontosCerroAzul.add(p175);
        p175.setPontoDeOnibus(true);
        Ponto p176 = new Ponto(176, -23.44804982677468, -51.92357540130615);
        listaPontosCerroAzul.add(p176);
        p176.setPontoDeOnibus(true);
        Ponto p177 = new Ponto(177, -23.446868678039564, -51.922545433044434);
        listaPontosCerroAzul.add(p177);
        Ponto p178 = new Ponto(178, -23.445687518743835, -51.9248628616333);
        listaPontosCerroAzul.add(p178);
        p178.setPontoDeOnibus(true);
        Ponto p179 = new Ponto(179, -23.44312830404283, -51.930012702941895);
        listaPontosCerroAzul.add(p179);
        p179.setPontoDeOnibus(true);
        Ponto p180 = new Ponto(180, -23.441080896591227, -51.93254470825195);
        listaPontosCerroAzul.add(p180);
        p180.setPontoDeOnibus(true);
        Ponto p181 = new Ponto(181, -23.438245972364786, -51.932501792907715);
        listaPontosCerroAzul.add(p181);
        p181.setPontoDeOnibus(true);
        Ponto p182 = new Ponto(182, -23.432930325536972, -51.93503379821777);
        listaPontosCerroAzul.add(p182);
        p182.setPontoDeOnibus(true);
        Ponto p183 = new Ponto(183, -23.428480767922245, -51.93683624267578);
        listaPontosCerroAzul.add(p183);
        p183.setPontoDeOnibus(true);
        Ponto p184 = new Ponto(184, -23.426708778508928, -51.93962574005127);
        listaPontosCerroAzul.add(p184);
        p184.setPontoDeOnibus(true);
        Ponto p185 = new Ponto(185, -23.423401001405313, -51.939754486083984);
        listaPontosCerroAzul.add(p185);
        p185.setPontoDeOnibus(true);
        Ponto p186 = new Ponto(186, -23.419029883339913, -51.939711570739746);
        listaPontosCerroAzul.add(p186);

        List<Ponto> listaRequiao = new ArrayList<Ponto>();
        Ponto p187 = new Ponto(187, -23.459029883339913, -51.939611570739746);
        p187.setPontoDeOnibus(true);
        listaRequiao.add(p187);

        Linha l1 = new Linha("Casa/UEM", 11, listaPontos);
        Linha l2 = new Linha("AABB", 40, listaPontosAABB);
        Linha l3 = new Linha("Cerro Azul", 427, listaPontosCerroAzul);
        Linha l4 = new Linha("Conjunto Requiao", 19, listaRequiao);
        
        Bus onibus = new Bus(10, l1);
        Bus onibus2 = new Bus(12, l3);
        Bus onibus3 = new Bus(23, l3);
        Bus onibus4 = new Bus(4, l4);
        Bus onibus5 = new Bus(6, l2);
        Bus onibus6 = new Bus(7, l2);
        onibus.setPosicaoAtual(-23.429071, -51.938290);
        onibus2.setPosicaoAtual(-23.413009, -51.939599);
        onibus3.setPosicaoAtual(-23.438644, -51.936251);
        onibus4.setPosicaoAtual(-23.419008, -51.943270);
        onibus5.setPosicaoAtual(-23.419778102985283, -51.94443225860596);
        onibus6.setPosicaoAtual(-23.428126371939317, -51.956706047058105);
        
        linhaRepository.save(l1);
        linhaRepository.save(l2);
        linhaRepository.save(l3);
        linhaRepository.save(l4);
 		
	}
	
	
}
