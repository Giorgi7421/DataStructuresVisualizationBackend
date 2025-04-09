package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.WebBrowser;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class WebBrowserService extends DataStructureService {

    private final OperationUtils operationUtils;

    public WebBrowserService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils);
        this.operationUtils = operationUtils;
    }

    public Response visit(String name, String url) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.WEB_BROWSER);
        WebBrowser webBrowser = convertToWebBrowser(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                webBrowser,
                WebBrowser::visit,
                url
        );
    }

    public Response back(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.WEB_BROWSER);
        WebBrowser webBrowser = convertToWebBrowser(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                webBrowser,
                WebBrowser::back
        );
    }

    public Response forward(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.WEB_BROWSER);
        WebBrowser webBrowser = convertToWebBrowser(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                webBrowser,
                WebBrowser::forward
        );
    }

    private WebBrowser convertToWebBrowser(DataStructureState state) {
        WebBrowser webBrowser = new WebBrowser();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        webBrowser.setMemoryHistory(memoryHistoryDto);

        return webBrowser;
    }
}
