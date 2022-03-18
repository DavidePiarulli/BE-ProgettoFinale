package it.epicode.be.util;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;

import it.epicode.be.model.Comune;
import it.epicode.be.model.Provincia;
import it.epicode.be.repository.ComuneRepository;
import it.epicode.be.repository.ProvinciaRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CsvRunner implements CommandLineRunner {

	@Autowired
	ProvinciaRepository provinceRepository;

	@Autowired
	ComuneRepository comuneRepository;

	@Override
	public void run(String... args) throws Exception {
//		List<Provincia> province = initReaderProvince();
//		initReaderComuni(province);

	}

	private List<Provincia> initReaderProvince() {
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

	private void initReaderComuni(List<Provincia> listaProvince) {
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

	}
}
