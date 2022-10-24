select z_date,z_name,z_amount,z_type,z_io_div,z_remark,z_m_amount from t_zhangzu 
where 
   z_date like '2022%' 
or z_date like '2021%' 
order by z_date;