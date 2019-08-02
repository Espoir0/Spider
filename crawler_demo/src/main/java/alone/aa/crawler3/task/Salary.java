package alone.aa.crawler3.task;

public class Salary {
    public static Integer[] getSalary(String salaryStr){
        //声明存放薪水范围的数组
        Integer[] salary=new Integer[2];
        /*
            500/天
            0.8-1.2万/月
            5-8千/月
            5-6万/年
        */
        String date=salaryStr.substring(salaryStr.length()-1,salaryStr.length());
        //如果是按照天则直接乘以240进行计算
        if(!"月".equals(date) && !"年".equals(date)){
            salaryStr = salaryStr.substring(0, salaryStr.length() - 2);
            salary[0]=salary[1]=str2Num(salaryStr,240);
            return salary;
        }

        String unit=salaryStr.substring(salaryStr.length()-3,salaryStr.length()-2);
        String[] salarys=salaryStr.substring(0,salaryStr.length()-3).split("-");

        salary[0]=mathSalary(date,unit,salarys[0]);
        salary[1]=mathSalary(date,unit,salarys[1]);
        return salary;
    }

    /**
     *
     * @param date 时间
     * @param unit 工资单位
     * @param salaryStr 工资字符串
     * @return
     */
    private static Integer mathSalary(String date, String unit, String salaryStr) {
        Integer salary=0;
        //判断是否是万
        if ("万".equals(unit)){
            //如果是万，薪水乘 10000
            salary=str2Num(salaryStr,10000);
        }else {
            salary=str2Num(salaryStr,1000);
        }

        //判断时间是否是月
        if("月".equals(date)){
            salary=str2Num(salaryStr,12);
        }
        return salary;
    }

    private static Integer str2Num(String salaryStr, int num) {
        try {
            //将字符串转化为小数，必须用Number接收，否则会有精度丢失问题
            Number result=Float.parseFloat(salaryStr)*num;
            return result.intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
