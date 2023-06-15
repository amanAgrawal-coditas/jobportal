package com.portal.service.implementation;

import com.portal.entity.Location;
import com.portal.repository.LocationRepository;
import com.portal.response.LocationResponse;
import com.portal.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImplementation implements LocationService
{
    @Autowired
    private LocationRepository locationRepository;
    @Override
    public boolean addLocation(String locationName)
    {
        Location location=new Location();
        location.setLocationName(locationName);
        locationRepository.save(location);
        return true;
    }

    @Override
    public boolean deleteLocation(long id)
    {
        locationRepository.deleteById(id);
        return true;
    }

    @Override
    public List<LocationResponse> getAllLocation()
    {
        List<Location> locationList=locationRepository.findAll();
        List<LocationResponse>locationResponseList=locationList.stream().map(location->{
            LocationResponse locationResponse=new LocationResponse();
            locationResponse.setLocationId(location.getLocationId());
            locationResponse.setLocationName(location.getLocationName());
            return locationResponse;
        }).collect(Collectors.toList());
        return locationResponseList;

    }
}
