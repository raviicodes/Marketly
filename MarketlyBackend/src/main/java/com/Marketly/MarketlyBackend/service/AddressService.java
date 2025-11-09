package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Address;
import com.Marketly.MarketlyBackend.payload.AddressDTO;

import java.util.List;

public interface AddressService {
      public Address addNewAddress(AddressDTO address);
      public List<Address> getAddressByUser(String userName);
      public Address deleteAddress(String userName,long addressId);
}
