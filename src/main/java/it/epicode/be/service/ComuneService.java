package it.epicode.be.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;

import it.epicode.be.model.Comune;
import it.epicode.be.model.Provincia;
import it.epicode.be.repository.ComuneRepository;
import it.epicode.be.repository.ProvinciaRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ComuneService {

	@Autowired
	ComuneRepository comuneRepository;

	@Autowired
	ProvinciaRepository provinceRepository;

	public Optional<Comune> findById(Long id) {
		return comuneRepository.findById(id);
	}

	public Page<Comune> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return comuneRepository.findAll(pageable);
	}

	public List<Comune> findByNome(String nome) {
		return comuneRepository.findByNomeStartingWithIgnoreCase(nome);
	}

	public Page<Comune> findAll(Pageable pageable) {
		return comuneRepository.findAll(pageable);
	}

	public List<Comune> findAll() {
		return comuneRepository.findAll();
	}

	public List<Comune> initComune(List<Provincia> listaProvince) {
		List<List<String>> records = new ArrayList<List<String>>();
		CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
		var fileName = "src/main/resources/comuni-italiani.csv";
		Path myPath = Paths.get(fileName);

		try (var br = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);
				var reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {
			String[] values = null;
			reader.readNext();
			provinceRepository.saveAll(listaProvince);
			while ((values = reader.readNext()) != null) {
				records.add(Arrays.asList(values));
				Optional<Provincia> provinciaTemp = provinceRepository.findByNome(values[3]);
				if (provinciaTemp.isPresent()) {
					comuneRepository.save(new Comune(values[1], values[2], provinciaTemp.get(), values[0]));

				}
			}
			log.info("Reading comuni-italiani.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Comune> listaComuni = new ArrayList<>();
		listaComuni.addAll(comuneRepository.findAll());
		return listaComuni;

	}
}
