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

import it.epicode.be.model.Provincia;
import it.epicode.be.repository.ProvinciaRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProvinciaService {

	@Autowired
	ProvinciaRepository provinciaRepository;

	public Optional<Provincia> findById(Long id) {
		return provinciaRepository.findById(id);
	}

	public List<Provincia> findByNome(String nome) {
		return provinciaRepository.findByNomeStartingWithIgnoreCase(nome);
	}

	public Page<Provincia> findAll(Pageable pageable) {
		return provinciaRepository.findAll(pageable);
	}

	public List<Provincia> initProvincia() {
		List<List<String>> records = new ArrayList<List<String>>();
		List<Provincia> provTemp = new ArrayList<>();

		CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
		var fileName = "src/main/resources/province-italiane.csv";
		Path myPath = Paths.get(fileName);

		try (var br = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);
				var reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {
			String[] values = null;
			reader.readNext();
			while ((values = reader.readNext()) != null) {
				records.add(Arrays.asList(values));
				provTemp.add(new Provincia(values[0], values[1], values[2]));

			}
			log.info("Reading province-italiane.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return provTemp;

	}

	public Page<Provincia> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return provinciaRepository.findAll(pageable);
	}

}
