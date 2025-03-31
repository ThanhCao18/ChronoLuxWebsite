package com.hau.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtil {
    public static Pageable getInstance(int page,int limit,String sortName,String sortBy){
            if(sortName != null && sortBy !=null){
                if(sortBy.equals("desc") ){
                    return  new PageRequest(page-1,limit, Sort.Direction.DESC,sortName);
                }
                else if(sortBy.equals("asc") ){
                    return  new PageRequest(page-1,limit, Sort.Direction.ASC,sortName);
                }
            }
            else{
                return new PageRequest(page-1,limit);
            }

        return null;
    }

}
