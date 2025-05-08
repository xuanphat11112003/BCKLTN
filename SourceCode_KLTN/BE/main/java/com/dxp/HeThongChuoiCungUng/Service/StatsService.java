package com.dxp.HeThongChuoiCungUng.Service;

import java.util.List;
import java.util.Map;

public interface StatsService {
    public Map<String, Object> getMonthlyReport(int month, int year);
    public Map<String, Object> getCurrentMonthReport();
    public Map<String, List<Long>> getMonthlyReport(int year);
}
