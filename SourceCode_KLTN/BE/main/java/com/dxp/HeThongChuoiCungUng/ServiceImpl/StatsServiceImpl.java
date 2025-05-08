package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Repository.ImportOrderRepository;
import com.dxp.HeThongChuoiCungUng.Repository.OrderExportRepository;
import com.dxp.HeThongChuoiCungUng.Service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StatsServiceImpl implements StatsService {
    @Autowired
    private ImportOrderRepository importOrderRepository;
    @Autowired
    private OrderExportRepository orderExportRepository;
    @Override
    public Map<String, Object> getMonthlyReport(int month, int year) {
        List<Object[]> weeklyCostsData = importOrderRepository.findTotalCostByWeek(month, year);
        List<Object[]> weeklyRevenueData = orderExportRepository.findTotalRevenueByWeek(month, year);

        List<Long> weeklyCosts = new ArrayList<>();
        for (Object[] row : weeklyCostsData) {
            BigDecimal totalCost = (BigDecimal) row[1];
            weeklyCosts.add(totalCost != null ? totalCost.longValue() : 0L);
        }

        List<Long> weeklyRevenues = new ArrayList<>();
        for (Object[] row : weeklyRevenueData) {
            BigDecimal totalRevenue = (BigDecimal) row[1];
            weeklyRevenues.add(totalRevenue != null ? totalRevenue.longValue() : 0L);
        }

        // Tạo báo cáo
        Map<String, Object> report = new HashMap<>();
        report.put("WeeklyCosts", weeklyCosts);
        report.put("WeeklyRevenue", weeklyRevenues);
        report.put("Month", month);
        report.put("Year", year);

        return report;
    }


    @Override
    public Map<String, Object> getCurrentMonthReport() {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentYear = cal.get(Calendar.YEAR);

        return getMonthlyReport(currentMonth, currentYear);
    }

    @Override
    public Map<String, List<Long>> getMonthlyReport(int year) {
        List<Object[]> costs = importOrderRepository.findMonthlyTotalCost(year);
        List<Object[]> revenues = orderExportRepository.findMonthlyTotalRevenue(year);

        Map<String, List<Long>> report = new HashMap<>();
        List<Long> monthlyCosts = new ArrayList<>(Collections.nCopies(12, 0L));
        List<Long> monthlyRevenues = new ArrayList<>(Collections.nCopies(12, 0L));

        for (Object[] cost : costs) {
            int month = (Integer) cost[0];
            Long totalCost = (Long) cost[1];
            monthlyCosts.set(month - 1, totalCost);
        }

        for (Object[] revenue : revenues) {
            int month = (Integer) revenue[0];
            Long totalRevenue = (Long) revenue[1];
            monthlyRevenues.set(month - 1, totalRevenue);
        }

        report.put("monthlyCosts", monthlyCosts);
        report.put("monthlyRevenues", monthlyRevenues);
        return report;
    }
}
