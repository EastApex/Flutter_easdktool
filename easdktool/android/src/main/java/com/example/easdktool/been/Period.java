package com.example.easdktool.been;

import com.apex.bluetooth.model.EABlePeriod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Period {
   public List<PeriodItem> periodItem;


   /// 持续天数,范围 5~10
   public int keepDay = 5;

   /// 周期,范围 20~45
   public int cycleDay = 28;

   /// 月经开始日期(必须为 yyyy-MM-dd)
   public String startDate = "2022-01-01";


   public EABlePeriod getPeriodDate()  {

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date date = null;
      try {
         date = simpleDateFormat.parse(startDate);
      } catch (ParseException e) {
         e.printStackTrace();
      }
//经期开始时间
      long periodStartTime = date.getTime();
      long oriPeriodStartTime = periodStartTime;
//判断当前时间是否在经期设置时间
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      long cTime = calendar.getTimeInMillis();//当天时间
      int surplusDay = (int) (((cTime - periodStartTime) / 1000 / 60 / 60 / 24) % cycleDay);
      if (surplusDay != 0) {//当天正是经期开始时间
         calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - surplusDay);
         periodStartTime = calendar.getTimeInMillis();
      }
      calendar.clear();
      calendar.setTimeInMillis(periodStartTime);
      calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + keepDay);
//经期结束时间
      long periodEndTime = calendar.getTimeInMillis();
//下一次排卵期时间
      calendar.clear();
      calendar.setTimeInMillis(periodStartTime);
      calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + cycleDay - 14);
      long nextOvulation = calendar.getTimeInMillis();
//易孕期开始时间
      calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 5);
      long ovulationStartTime = calendar.getTimeInMillis();
//易孕期结束时间
      calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 9);
      long ovulationEndTime = calendar.getTimeInMillis();
//同步经期数据给手表
      List<EABlePeriod.EABlePeriodData> periodDataList = new ArrayList<>();
      EABlePeriod eaBlePeriod = new EABlePeriod();

      for (int i = 0; i < cycleDay; i++) {
         EABlePeriod.EABlePeriodData eaBlePeriodData = new EABlePeriod.EABlePeriodData();
         calendar.clear();
         calendar.setTimeInMillis(periodStartTime);
         calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + i);
         long currentTime = calendar.getTimeInMillis();

         if (currentTime < periodEndTime) {
            eaBlePeriodData.setPeriodType(EABlePeriod.PeriodType.menstrual);
            eaBlePeriodData.setDays(i + 1);

            if (ovulationStartTime <= periodEndTime) {
               ovulationStartTime = periodEndTime;
            }
         } else if (currentTime >= periodEndTime && currentTime < ovulationStartTime) {
            eaBlePeriodData.setPeriodType(EABlePeriod.PeriodType.safety_period_1);
            eaBlePeriodData.setDays((int) ((ovulationStartTime - currentTime) / 1000 / 3600 / 24));

         }
         else if (currentTime >= ovulationStartTime && currentTime < ovulationEndTime) {
            eaBlePeriodData.setPeriodType(EABlePeriod.PeriodType.ovulation);
            eaBlePeriodData.setDays((int) ((currentTime - ovulationStartTime) / 1000 / 3600 / 24));

         } else if (currentTime >= ovulationEndTime) {
            eaBlePeriodData.setPeriodType(EABlePeriod.PeriodType.safety_period_2);
            calendar.clear();
            calendar.setTimeInMillis(periodStartTime);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + cycleDay);
            eaBlePeriodData.setDays((int) ((calendar.getTimeInMillis() - currentTime) / 24 / 3600 / 1000));

         }
         eaBlePeriodData.setTime_stamp(currentTime / 1000);
         periodDataList.add(eaBlePeriodData);
      }
      eaBlePeriod.dataList = periodDataList;
      return eaBlePeriod;
   }
}
