package it.Gruppo1.NowPuglia.serviceImp;

import it.Gruppo1.NowPuglia.dto.JsonResponse.AriaDto;
import it.Gruppo1.NowPuglia.dto.JsonResponse.EnergiaDto;
import it.Gruppo1.NowPuglia.dto.JsonResponse.JsonDataDto;
import it.Gruppo1.NowPuglia.dto.JsonResponse.MisurazioniDto;
import it.Gruppo1.NowPuglia.model.CittaModel;
import it.Gruppo1.NowPuglia.model.EnergiaModel;
import it.Gruppo1.NowPuglia.model.SensoriModel;
import it.Gruppo1.NowPuglia.model.ValoriInquinantiModel;
import it.Gruppo1.NowPuglia.repository.ICittaRepository;
import it.Gruppo1.NowPuglia.repository.IEnergiaRepository;
import it.Gruppo1.NowPuglia.repository.ISensoriRepository;
import it.Gruppo1.NowPuglia.repository.IValoriInquinantiRepository;
import it.Gruppo1.NowPuglia.service.IJsonResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class JsonResponseService implements IJsonResponseService {
    private final IValoriInquinantiRepository iValoriInquinantiRepository;
    private final IEnergiaRepository iEnergiaRepository;
    private final ISensoriRepository iSensoriRepository;
    private final ICittaRepository iCittaRepository;

    @Autowired
    public JsonResponseService (IValoriInquinantiRepository iValoriInquinantiRepository, IEnergiaRepository iEnergiaRepository, ISensoriRepository iSensoriRepository, ICittaRepository iCittaRepository) {
        this.iValoriInquinantiRepository = iValoriInquinantiRepository;
        this.iEnergiaRepository = iEnergiaRepository;
        this.iSensoriRepository = iSensoriRepository;
        this.iCittaRepository = iCittaRepository;
    }

    @Override
    public JsonDataDto runResponse() {
        return new JsonDataDto(
                iValoriInquinantiRepository.count(),
                misurazioniList()
        );
    }

    private LinkedList<MisurazioniDto> misurazioniList () {
        LinkedList<MisurazioniDto> misurazioniList = new LinkedList<>();

        List<CittaModel> citta = iCittaRepository.findAll();
        List<EnergiaModel> energia = iEnergiaRepository.findAll();
        List<SensoriModel> sensori = iSensoriRepository.findAll();
        List<ValoriInquinantiModel> valoriInquinanti = iValoriInquinantiRepository.findAll();

        for (CittaModel c : citta) {
            List<EnergiaModel> energiaCorrispondente = energia.stream()
                    .filter(e -> e.getCittaInfo().getId() == c.getId())
                    .toList();

            List<SensoriModel> sensoreCorrispondente = sensori.stream()
                    .filter(s -> s.getCittaInfo().getId() == c.getId())
                    .toList();

            for (EnergiaModel e : energiaCorrispondente) {
                for (SensoriModel s : sensoreCorrispondente) {
                    List<ValoriInquinantiModel> valoriInquinantiCorrispondenti = valoriInquinanti.stream()
                            .filter(v -> v.getSensoreInfo().getId() == s.getId())
                            .toList();

                    for (ValoriInquinantiModel v : valoriInquinantiCorrispondenti) {
                        MisurazioniDto misurazione = new MisurazioniDto();
                        misurazione.setCitta(c.getNomeCitta());

                        EnergiaDto energiaDto = new EnergiaDto();
                        energiaDto.setFonte(e.getFonte());
                        energiaDto.setPotenza(e.getPotenza());
                        misurazione.setEnergiaRinnovabileProdotta(energiaDto);

                        AriaDto ariaDto = new AriaDto();
                        ariaDto.setTipoValoreInquinante(v.getTipoValore());
                        ariaDto.setValoreInquinante(v.getValore());
                        ariaDto.setLatitudine(s.getLatitude());
                        ariaDto.setLongitudine(s.getLongitude());

                        misurazione.setValoriAriaInquinata(ariaDto);
                        misurazioniList.add(misurazione);
                    }
                }
            }
        }

        return misurazioniList;
    }


}
