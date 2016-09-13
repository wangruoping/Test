package cn.pxl.app.ms.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.pxl.app.ms.dto.CommentsDto;
import cn.pxl.app.ms.dto.MagzinesCategoryDto;
import cn.pxl.app.ms.dto.MagzinesDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.PrintDto;
import cn.pxl.app.ms.entity.MagzinesCategoryEntity;
import cn.pxl.app.ms.entity.MagzinesEntity;
import cn.pxl.app.ms.form.PrintAmountFrom;

public interface MagzinesService {

    public void save(MagzinesEntity magzinesEntity);

    public PagingDto<MagzinesDto> pagingList(int offset, int limit, int i);

    public String uploadMagzine(MultipartFile file,MultipartFile prev1,MultipartFile prev2,MultipartFile prev3, String tplName, String fileComment, String userid, String address, String phone, String youbian, String payAmount, String magAmount, String flag, String magCateId);

    public List<CommentsDto> getCommentsList(String magId,String commentDate);

    public String insertComment(String magId, String commentContent, String userid);

    public String insertAgree(String magId,String userid);

    public MagzinesEntity findById(String magId);

    public File download(String fileId);
    
    public File downloadZoomMagzines(String fileId);

    public File downloadOrigin(String fileId);

    public Map<String, String> getAgreesCount(String magId, String userid);

    public Map<String, List<MagzinesEntity>> getFriendMagList(String userid);

    public String insertMagZf(String magId, String zfCommentContent, String userid);

    public Map<String, String> getMagZfListCount(String magId);

    public List<MagzinesDto> getUserMagList(String userid, Integer pageIndex);

	public File getMagImageList(String magid,
			String imageName);

	public Map<String, List<MagzinesDto>> getFriendMagDetailList(String userid, Integer pageIndex, Integer pageSize);

	public PagingDto<MagzinesCategoryDto> magzinesCategoryList(int offset, int limit);

	public MagzinesCategoryEntity getmMagzinesCategoryInfo(
			String categoryId);

	public boolean updateMagzinesCategory(
			MagzinesCategoryEntity magzinesCategoryEntity);

	public boolean addMagzinesCategory(
			MagzinesCategoryEntity magzinesCategoryEntity);

	public boolean deleteMagCategoryList(String[] categoryIdStrings);

	public Integer magzinesCategoryCount();

	public List<MagzinesCategoryEntity> getAllCategoryList();

	public int uploadProductMagzines(CommonsMultipartFile file, String categoryId, String fileComment, String productAmount);

	public boolean deleteMagProductList(String[] productIdsStrings);

	public List<MagzinesEntity> getMagProductList(String categoryId,
			int top);

	public List<MagzinesDto> getAllMagProductList(int orderType,
			String searchText);

	public List<String> getHotMagProductList(int top);

	public void updateMagzines(MagzinesEntity magzinesEntity);

	public boolean findUserBuyPro(String userid, String magId);

	public String insertConsumeInfo(String magId, String userid, String consumeFlag);

	public void insertDownloadEntity(String magId, String string);

	public List<MagzinesDto> getUserMagAllList(String userid);

	public Map<String, List<MagzinesDto>> getShopMagDetailList(String userid,
			String searchText, int parseInt, int pageSize);

	public List<MagzinesCategoryDto> getCategorysList();

	public PagingDto<PrintDto> printList(int offset, int limit, int i);

	public void setPrintAmount(PrintAmountFrom printAmountFrom);

	public String getPrintAmount();

	public List<MagzinesEntity> getMagDetailProductList(String categoryId,
			String pageIndex);
	
	public int cfMagzines(String magId, String id);

	public int magCfMagzines(String magId, String id);
}
