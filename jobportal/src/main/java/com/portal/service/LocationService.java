package com.portal.service;


import com.portal.response.LocationResponse;

import java.util.List;

public interface LocationService
{
    boolean addLocation(String locationName);
    boolean deleteLocation(long id);
    List<LocationResponse> getAllLocation();
}
