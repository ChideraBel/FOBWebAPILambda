package org.tranqwave.fobwebapilambda.section;

import dao.SectionDao;
import dao.dbModels.DynamoDBSection;
import model.GetAllSectionsResponse;
import model.Section;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllSectionsLambda {
    private final SectionDao sectionDao;

    public GetAllSectionsLambda(SectionDao sectionDao) { this.sectionDao  = sectionDao;}

    /*
     Gets all sections
     */
    public GetAllSectionsResponse getAllSections() {
        final List<DynamoDBSection> dynamoDBSections = sectionDao.getAllSections();

        final List<Section> sectionList = dynamoDBSections.stream().map(x -> toSection(x)).collect(Collectors.toList());

        return new GetAllSectionsResponse(sectionList);
    }

    private Section toSection(DynamoDBSection dynamoDBSection) {
        return Section.builder()
                .section_id(dynamoDBSection.getSection_id())
                .name(dynamoDBSection.getSection_name())
                .content(dynamoDBSection.getSection_content())
                .description(dynamoDBSection.getSection_description())
                .build();
    }
}
