package com.mobimeo.citynavigation.dao.intializers;


import com.mobimeo.citynavigation.dao.TimesRepository;
import com.mobimeo.citynavigation.dao.model.Time;
import com.mobimeo.citynavigation.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

@Component
public class TimesInitializer {

    private static final Logger log = LoggerFactory.getLogger(TimesInitializer.class);

    @Autowired
    public TimesInitializer(TimesRepository repository) throws Exception {

        if (repository.count() != 0) {
            return;
        }

        List<Time> timeList = readTimes();
        log.info("Importing {} Times into DataBase…", timeList.size());
        repository.saveAll(timeList);
        log.info("Successfully imported {} Times.", repository.count());
    }

    /**
     * Reads a file {@code times.csv} from the class path and parses it into {@link Time} instances about to
     * persisted.
     *
     * @return
     * @throws Exception
     */
    public static List<Time> readTimes() throws Exception {

        ClassPathResource resource = new ClassPathResource("data/times.csv");
        Scanner scanner = new Scanner(resource.getInputStream());
        String line = scanner.nextLine();
        scanner.close();

        FlatFileItemReader<Time> itemReader = new FlatFileItemReader<Time>();
        itemReader.setResource(resource);

        // DelimitedLineTokenizer defaults to comma as its delimiter
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(line.split(","));
        tokenizer.setStrict(false);

        DefaultLineMapper<Time> lineMapper = new DefaultLineMapper<Time>();
        lineMapper.setFieldSetMapper(fields -> {

            Long lineId = fields.readLong("line_id");
            Long stopId = fields.readLong("stop_id");


            Timestamp timestamp= DateUtils.converToTimeStamp(fields.readString("time"));
            return new Time(lineId, stopId, timestamp);
        });

        lineMapper.setLineTokenizer(tokenizer);
        itemReader.setLineMapper(lineMapper);
        itemReader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
        itemReader.setLinesToSkip(1);
        itemReader.open(new ExecutionContext());

        List<Time> timeList = new ArrayList<>();
        Time time = null;

        do {

            time = itemReader.read();

            if (time != null) {
                timeList.add(time);
            }

        } while (time != null);

        return timeList;
    }

}

