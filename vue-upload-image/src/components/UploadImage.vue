<template>
  <div>
    <div class="row">
      <div class="col-8">
        <label class="btn btn-default p-0">
          <!-- 파일 선택 상자 -->
          <input
            type="file"
            accept="image/*"
            ref="file"
            @change="selectImage"
          />
        </label>
      </div>

      <div class="col-4">
        <!-- 서버에 insert문 메소드 호출 -->
        <button
          class="btn btn-success btn-sm float-right"
          :disabled="!currentImage"
          @click="upload"
        >
          Upload
        </button>
      </div>

      <!-- 프로그래스 바 시작-->
      <div v-if="currentImage" class="progress">
        <div
          class="progress-bar"
          role="progressbar"
          :style="{ width: progress + '%' }"
          :aria-valuenow="progress"
          aria-valuemin="0"
          aria-valuemax="100"
        >
          {{ progress }}%
        </div>
      </div>
      <!-- 프로그래스 바 종료 -->

      <!-- 미리보기 이미지 시작 -->
      <div v-if="previewImage">
        <div>
          <img class="preview my-3" :src="previewImage" alt="" />
        </div>
      </div>
      <!-- 미리보기 이미지 종료 -->

      <!-- 서버 에러 메세지가 있을 경우 아래 출력 -->
      <div v-if="message" class="alert alert-secondary" role="alert">
        {{ message }}
      </div>

      <!-- list(목록) 이미지 정보를 출력 -->
      <div class="card mt-3">
        <div class="card-header">List of Image</div>
        <ul class="list-group list-group-flush">
          <li class="list-group-item"
              v-for="(image, index) in imageInfo"
              :key="index"
              >
              <a :href="image.url">{{ image.name }}</a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import UploadService from "../services/UploadFilesService";

export default {
  name: "upload-image",
  data() {
    // 변수 초기화
    return {
      currentImage: undefined, // 현재이미지
      previewImage: undefined, // 미리보기 이미지
      progress: 0, // 프로그래스바를 위한 변수
      message: "",
      imageInfo: [], // 이미지 정보 객체 배열
    };
  },
  methods: {
    // 이미지를 선택하면 변수에 저장하는 메소드
    selectImage() {
      this.currentImage = this.$refs.file.files.item(0);
      this.previewImage = URL.createObjectURL(this.currentImage);
      this.progress = 0;
      this.message = "";
    },
    // 파일 업로드를 위한 메소드
    upload() {
      this.progress = 0;

      // 서버에 이미지 업로드 요청(insert문 실행)
      UploadService.upload(this.currentImage, (event) => {
        // 파일이 업로드될 때 진척 상황이 저장됨
        this.progress = Math.round((100 * event.loaded) / event.total);
      })
        .then((response) => {
          // 서버쪽 응답 메세지 저장
          this.message = response.data.message;
          // 서버쪽에 insert가 잘 되었는지 select문 호출
          return UploadService.getFiles();
        })
        // 성공하면 then으로 들어옴
        .then((image) => {
          this.imageInfo = image.data;
        })
        // 실패하면 catch로 들어옴
        .catch((err) => {
          this.progress = 0;
          this.message = "Could not upload the image!" + err;
          this.currentImage = undefined;
        });
    },
  },
  // 화면이 뜨자마자 실행되는 이벤트
  mounted() {
    // springboot 서버에 이미지파일 요청
    UploadService.getFiles()
      // 성공하면 then에 들어옴
      .then((response) => {
        // response.data : 서버쪽에서 전송된 객체
        this.imageInfo = response.data;
      });
  },
};
</script>

<style>
.preview {
  max-width: 200;
}
</style>
