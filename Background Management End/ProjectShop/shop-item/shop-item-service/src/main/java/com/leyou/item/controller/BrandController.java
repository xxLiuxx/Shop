package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc) {
        PageResult<Brand> result = this.brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
        if(CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    /**
     * 添加新品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam List<Long> cids) {
        this.brandService.saveBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据cid查询品牌列表
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable Long cid) {
        List<Brand> brands = this.brandService.queryBrandByCid(cid);
        if(CollectionUtils.isEmpty(brands)) {
            ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(brands);
    }

    /**
     * 根据商品id查询商品
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandByBrandId(@PathVariable Long id) {
        Brand brand = this.brandService.queryById(id);
        if(brand == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }
}
