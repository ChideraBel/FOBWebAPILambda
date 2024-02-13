package org.tranqwave.fobwebapilambda.section;

import com.amazonaws.services.lambda.runtime.Context;
import dao.SectionDao;
import dao.dbModels.DynamoDBSection;
import model.GetAllSectionsResponse;
import model.Section;
import utils.ConstantUtils;

import java.util.List;
import java.util.stream.Collectors;

import static utils.ConstantUtils.toSection;

public class GetAllSectionsLambda {
    private final SectionDao sectionDao;

    public GetAllSectionsLambda(SectionDao sectionDao) { this.sectionDao  = sectionDao;}

    /*
     Gets all sections
     */
    public GetAllSectionsResponse getAllSections(Context context) {
        final List<DynamoDBSection> dynamoDBSections = sectionDao.getAllSections();

        final List<Section> sectionList = dynamoDBSections.stream().map(x -> toSection(x)).collect(Collectors.toList());

        //Log action
        context.getLogger().log(String.format("Retrieved all %d sections from FOBSectionTable", sectionList.size()));

        return new GetAllSectionsResponse(sectionList);
    }
}