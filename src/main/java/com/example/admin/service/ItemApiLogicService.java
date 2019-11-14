package com.example.admin.service;

import com.example.admin.ifs.CrudInterface;
import com.example.admin.model.entity.Item;
import com.example.admin.model.network.Header;
import com.example.admin.model.network.request.ItemApiRequest;
import com.example.admin.model.network.response.ItemApiResponse;
import com.example.admin.repository.ItemRepository;
import com.example.admin.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {

    @Autowired
    private PartnerRepository partnerRepository;


    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        return null;
    }

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        ItemApiRequest body = request.getData();

        Item item = Item.builder()
                .status(body.getStatus())
                .name(body.getName())
                .title(body.getTitle())
                .content(body.getContent())
                .price(body.getPrice())
                .brandName(body.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(body.getPartnerId()))
                .build();

        Item newItem = baseRepository.save(item);
        return response(newItem);
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        return baseRepository.findById(id)
        .map(item-> response(item))
        .orElseGet(()-> Header.ERROR("No Data"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest body = request.getData();
        return baseRepository.findById(body.getId())
                .map(item-> {
                    item.setStatus(body.getStatus())
                            .setName(body.getName() )
                            .setTitle(body.getTitle())
                            .setContent(body.getContent())
                            .setPrice(body.getPrice())
                            .setBrandName(body.getBrandName())
                            .setRegisteredAt(body.getRegisteredAt())
                            .setUnregisteredAt(body.getUnregisteredAt());
                    return item;
                })
                .map(updatedItem -> baseRepository.save(updatedItem))
                .map(item -> response(item))
                .orElseGet(()->Header.ERROR("No Data"));
    }

    @Override
    public Header delete(Long id) {

        return baseRepository.findById(id)
                .map(item->{
                    baseRepository.delete(item);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("No Data"));
    }

    public Header<ItemApiResponse> response(Item item){

        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return Header.OK(body);
    }
}
