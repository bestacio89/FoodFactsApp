package org.Foodinfo.Helpers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.Foodinfo.Domain.Product;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ParsingHelper {

    public static List<Product> parseCsvFile(String fileName, String[] headers) throws Exception {
        FileReader reader = null;
        try {
            reader = new FileReader(fileName);

            HeaderColumnNameMappingStrategy<Product> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Product.class); // Set the bean class to Product

            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
