package com.octagon.gestionclientes.client;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class EntityNotFoundException extends Exception implements ExceptionNotWrappedByHystrix {

}
