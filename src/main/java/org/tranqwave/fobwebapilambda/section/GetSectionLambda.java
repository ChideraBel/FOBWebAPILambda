package org.tranqwave.fobwebapilambda.section;

import dao.SectionDao;
import dao.dbModels.DynamoDBSection;
import model.GetSectionRequest;
import model.GetSectionResponse;
import model.Request;
import model.Section;

import javax.naming.Context;

public class GetSectionLambda {
    private final SectionDao sectionDao;

    public GetSectionLambda(SectionDao sectionDao) {this.sectionDao = sectionDao;}

    public GetSectionResponse getSection(GetSectionRequest request) {
        final DynamoDBSection dynamoDBSection =  sectionDao.getSection(request.toString());

        final Section section = toSection(dynamoDBSection);

        return new GetSectionResponse(section);
    }

    public Section toSection(DynamoDBSection dynamoDBSection) {
        return Section.builder()
                .section_id(dynamoDBSection.getSection_id())
                .name(dynamoDBSection.getSection_name())
                .content(dynamoDBSection.getSection_content())
                .description(dynamoDBSection.getSection_description())
                .build();
    }
}
