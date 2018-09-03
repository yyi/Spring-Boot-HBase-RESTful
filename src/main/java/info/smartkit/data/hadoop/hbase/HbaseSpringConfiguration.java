package info.smartkit.data.hadoop.hbase;

import org.apache.hadoop.conf.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.hadoop.hbase.HbaseConfigurationFactoryBean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

@org.springframework.context.annotation.Configuration
public class HbaseSpringConfiguration {

    @Bean("HbaseConfigurationFactoryBean")
    public  HbaseConfigurationFactoryBean hbaseConfigurationFactoryBean(){
        HbaseConfigurationFactoryBean hbaseConfigurationFactoryBean = new HbaseConfigurationFactoryBean();
        hbaseConfigurationFactoryBean.setZkQuorum("192.168.100.3");
        hbaseConfigurationFactoryBean.setZkPort(2181);
        return  hbaseConfigurationFactoryBean ;
    }

    @Bean("hbaseConfiguration")
    @DependsOn("HbaseConfigurationFactoryBean")
    public Configuration hbaseConfiguration() {
        return hbaseConfigurationFactoryBean().getObject();
    }

    @Bean
    @DependsOn("hbaseConfiguration")
    HbaseTemplate hbaseTemplate(){
        HbaseTemplate hbaseTemplate = new HbaseTemplate();
        hbaseTemplate.setConfiguration(hbaseConfiguration());
        return hbaseTemplate;
    }


}
