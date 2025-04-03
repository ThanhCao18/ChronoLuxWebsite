package com.haui.data;

import com.haui.entity.VoucherConfig;
import com.haui.repository.VoucherConfigRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationListener;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class  MyAppInitializer  implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private VoucherConfigRepository voucherRepository;
    @Autowired
    private VoucherConfigRepository voucherConfigRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            voucherConfigRepository.findAll().stream().findFirst()
                    .orElseGet(() -> {
                        VoucherConfig voucherConfig = new VoucherConfig();
                        voucherConfig.setDiscountDefault(200000);
                        voucherConfig.setPrefix("NGMOI_");
                        return  voucherRepository.save(voucherConfig);
                    } );
        }
    }
}
