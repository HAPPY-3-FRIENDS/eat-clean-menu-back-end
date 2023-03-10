package com.happy3friends.eatcleanmenubackend.service.serviceImpl;

import com.happy3friends.eatcleanmenubackend.dto.CustomUserDietaryInfoDTO;
import com.happy3friends.eatcleanmenubackend.dto.UserDietaryInfoDTO;
import com.happy3friends.eatcleanmenubackend.entity.UserDietaryInfoEntity;
import com.happy3friends.eatcleanmenubackend.entity.UsersEntity;
import com.happy3friends.eatcleanmenubackend.exception.NotFoundException;
import com.happy3friends.eatcleanmenubackend.mapper.UserDietaryInfoMapper;
import com.happy3friends.eatcleanmenubackend.repository.UserDietaryInfoRepository;
import com.happy3friends.eatcleanmenubackend.repository.UserRepository;
import com.happy3friends.eatcleanmenubackend.service.UserDietaryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDietaryInfoServiceImpl implements UserDietaryInfoService {
    @Autowired
    private UserDietaryInfoRepository userDietaryInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDietaryInfoMapper mapper;

    @Override
    public void createUserDietaryInfoByUserId(CustomUserDietaryInfoDTO customUserDietaryInfoDTO, int userId) {
        UserDietaryInfoEntity existed = userDietaryInfoRepository.findByUserId(userId);
        Optional<UsersEntity> users = userRepository.findById(userId);

        /*double calories = calculateCaloriesConsumedByBmrAndActivityRateAndDietTarget(
                customUserDietaryInfoDTO.getBmr(),
                customUserDietaryInfoDTO.getActivityRate(),
                customUserDietaryInfoDTO.getDietTarget()
        );*/

        double calories = customUserDietaryInfoDTO.getCaloriesConsumed();

        if (users.get() != null) {
            users.get().setGender(customUserDietaryInfoDTO.getGender());
            userRepository.save(users.get());
        } else throw new NotFoundException("Can not find user with userId: " + userId);

        UserDietaryInfoDTO userDietaryInfoDTO = new UserDietaryInfoDTO();
        if (existed != null) {
            userDietaryInfoDTO.setId(existed.getId());
        }
        userDietaryInfoDTO.setDietTarget(customUserDietaryInfoDTO.getDietTarget());
        userDietaryInfoDTO.setUserAge(customUserDietaryInfoDTO.getUserAge());
        userDietaryInfoDTO.setUserHeight(customUserDietaryInfoDTO.getUserHeight());
        userDietaryInfoDTO.setUserWeight(customUserDietaryInfoDTO.getUserWeight());
        userDietaryInfoDTO.setActivityRate(customUserDietaryInfoDTO.getActivityRate());
        userDietaryInfoDTO.setBmi(customUserDietaryInfoDTO.getBmi());
        userDietaryInfoDTO.setBmr(customUserDietaryInfoDTO.getBmr());
        userDietaryInfoDTO.setUserId(userId);
        userDietaryInfoDTO.setCaloriesConsumed(calories);
        UserDietaryInfoEntity userDietaryInfoEntity = mapper.convertDTOToEntity(userDietaryInfoDTO);
        userDietaryInfoRepository.save(userDietaryInfoEntity);
    }

    @Override
    public double calculateCaloriesConsumedByBmrAndActivityRateAndDietTarget(double bmr, String activityRate, String dietTarget) {
        double calories = 0;
        switch (activityRate) {
            case "Kh??ng t???p luy???n":
                calories = bmr * 1.2;
                break;
            case "??t t???p luy???n":
                calories = bmr * 1.375;
                break;
            case "Luy???n t???p v???a":
                calories = bmr * 1.55;
                break;
            case "Luy???n t???p nhi???u":
                calories = bmr * 1.725;
                break;
            case "Luy???n t???p c?????ng ????? cao":
                calories = bmr * 1.9;
                break;
            default:
                throw new NotFoundException("Invalid activity rate!");
        }

        switch (dietTarget) {
            case "Gi???m c??n":
                calories -= 500;
                break;
            case "T??ng c??n":
                calories += 500;
                break;
            case "Gi??? nguy??n c??n n???ng":
                break;
            default:
                throw new NotFoundException("Invalid diet target!");
        }

        return calories;
    }
}
