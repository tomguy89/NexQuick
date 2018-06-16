package com.nexquick.service.parsing;

import java.util.List;

import com.nexquick.model.vo.Coordinate;

public interface OptimalRouteService {

	List<Coordinate> optimization(List<Coordinate> list);

}