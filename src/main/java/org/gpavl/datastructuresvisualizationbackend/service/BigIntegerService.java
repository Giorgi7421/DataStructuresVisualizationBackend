package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.BigInteger;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.security.UserService;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BigIntegerService extends DataStructureService {

    private final OperationUtils operationUtils;

    public BigIntegerService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            UserService userService,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils, userService);
        this.operationUtils = operationUtils;
    }

    public Response add(String name, String number) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.BIG_INTEGER);
        BigInteger bigInteger = convertToBigInteger(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                bigInteger,
                BigInteger::add,
                number
        );
    }

    public Response isGreaterThan(String name, String number) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.BIG_INTEGER);
        BigInteger bigInteger = convertToBigInteger(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                bigInteger,
                BigInteger::isGreaterThan,
                number
        );
    }

    private BigInteger convertToBigInteger(DataStructureState state) {
        BigInteger bigInteger = new BigInteger();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        bigInteger.setMemoryHistory(memoryHistoryDto);

        return bigInteger;
    }
}
