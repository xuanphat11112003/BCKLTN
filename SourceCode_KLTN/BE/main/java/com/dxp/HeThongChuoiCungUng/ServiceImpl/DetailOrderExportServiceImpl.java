package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Model.Detailexportorder;
import com.dxp.HeThongChuoiCungUng.Repository.DetailExportOrderRepository;
import com.dxp.HeThongChuoiCungUng.Service.DetailOrderExportService;
import com.dxp.HeThongChuoiCungUng.Specification.DetailExportSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DetailOrderExportServiceImpl implements DetailOrderExportService {
    @Autowired
    private DetailExportOrderRepository detailExportOrderRepository;
    @Override
    public void save(Detailexportorder detailexportorder) {
        this.detailExportOrderRepository.save(detailexportorder);
    }

    @Override
    public List<Map<String, Object>> getSalesDataByProduct() {
        LocalDate endDate = LocalDate.now().withDayOfMonth(1).minusDays(1);
        LocalDate startDate = endDate.minusMonths(11).withDayOfMonth(1);
        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate);
        List<Object[]> results = detailExportOrderRepository.getSalesDataByProduct(sqlStartDate, sqlEndDate);

        Map<Integer, List<Integer>> salesHistoryMap = new HashMap<>();

        for (Object[] row : results) {
            Integer productId = (Integer) row[0];
            Integer amount = ((Number) row[3]).intValue();


            salesHistoryMap.putIfAbsent(productId, new ArrayList<>());
            salesHistoryMap.get(productId).add(amount);
        }

        List<Map<String, Object>> finalResult = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : salesHistoryMap.entrySet()) {
            Map<String, Object> data = new HashMap<>();
            data.put("product_id", entry.getKey());
            data.put("sales_history", entry.getValue());
            int nextSales = calculateNextSales(entry.getValue());
            data.put("next_sales", nextSales);

            finalResult.add(data);
        }

        return finalResult;
    }

    @Override
    public List<Map<String, Object>> getSalesSalesHistoryByProduct() {
        LocalDate endDate = LocalDate.now().withDayOfMonth(1);
        LocalDate startDate = endDate.minusMonths(12);
        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate.minusDays(1));
        List<Object[]> results = detailExportOrderRepository.getSalesDataByProduct(sqlStartDate, sqlEndDate);

        Map<Integer, List<Integer>> salesHistoryMap = new HashMap<>();

        for (Object[] row : results) {
            Integer productId = (Integer) row[0];
            Integer amount = ((Number) row[3]).intValue();


            salesHistoryMap.putIfAbsent(productId, new ArrayList<>());
            salesHistoryMap.get(productId).add(amount);
        }

        List<Map<String, Object>> finalResult = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : salesHistoryMap.entrySet()) {
            Map<String, Object> data = new HashMap<>();
            data.put("product_id", entry.getKey());
            data.put("sales_history", entry.getValue());
            finalResult.add(data);
        }

        return finalResult;
    }

    public double calculateSafetyStock(List<Integer> salesHistory) {
        double zScore = 1.645;
        double leadTime = 1;
        double mean = salesHistory.stream().mapToDouble(i -> i).average().orElse(0.0);
        double variance = salesHistory.stream().mapToDouble(i -> Math.pow(i - mean, 2)).average().orElse(0.0);
        double standardDeviation = Math.sqrt(variance);

        return zScore * standardDeviation * Math.sqrt(leadTime);
    }

    public double calculateOrderQuantity(int inventory, int forecastedDemand, List<Integer> salesHistory) {
        double safetyStock = calculateSafetyStock(salesHistory);
        return (forecastedDemand + safetyStock) - inventory;
    }

    private int calculateNextSales(List<Integer> salesHistory) {
        int size = salesHistory.size();

        if (size == 0) return 0;
        if (size == 1) return salesHistory.get(0);
        if (size == 2) return (salesHistory.get(0) + salesHistory.get(1)) / 2;

        int sum = salesHistory.get(size - 1) + salesHistory.get(size - 2) + salesHistory.get(size - 3);
        return sum / 3;
    }

}
