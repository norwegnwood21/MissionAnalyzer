package org.example.web;

import org.example.model.Mission;
import org.example.service.MissionArchiveService;
import org.example.service.MissionImportService;
import org.example.service.MissionReportService;
import org.example.web.dto.MissionArchiveItemResponse;
import org.example.web.dto.MissionDetailsResponse;
import org.example.web.dto.MissionUploadResponse;
import org.example.web.mapper.MissionResponseMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionImportService missionImportService;
    private final MissionArchiveService missionArchiveService;
    private final MissionReportService missionReportService;

    public MissionController(MissionImportService missionImportService,
                             MissionArchiveService missionArchiveService,
                             MissionReportService missionReportService) {
        this.missionImportService = missionImportService;
        this.missionArchiveService = missionArchiveService;
        this.missionReportService = missionReportService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MissionUploadResponse upload(@RequestPart("file") MultipartFile file) throws Exception {
        MissionImportService.ParsedMissionResult result = missionImportService.parseAndValidate(file);
        Mission saved = missionArchiveService.save(result.mission());

        return new MissionUploadResponse(
                "Миссия успешно сохранена в архив",
                saved.getMissionId(),
                result.format()
        );
    }

    @GetMapping
    public List<MissionArchiveItemResponse> list() {
        return missionArchiveService.findAllMissions().stream()
                .map(MissionResponseMapper::toArchiveItem)
                .toList();
    }

    @GetMapping("/{missionId}")
    public MissionDetailsResponse getByMissionId(@PathVariable String missionId) {
        Mission mission = missionArchiveService.findDetailedByMissionId(missionId);
        return MissionResponseMapper.toDetails(mission);
    }

    @GetMapping(value = "/{missionId}/report", produces = MediaType.TEXT_PLAIN_VALUE)
    public String report(@PathVariable String missionId,
                         @RequestParam(defaultValue = "summary") String type) {
        Mission mission = missionArchiveService.findDetailedByMissionId(missionId);
        return missionReportService.generateReport(mission, type);
    }

    @DeleteMapping("/{missionId}")
    public String delete(@PathVariable String missionId) {
        missionArchiveService.deleteByMissionId(missionId);
        return "Миссия " + missionId + " удалена из архива";
    }
}