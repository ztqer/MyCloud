FROM nginx
#删除默认配置
RUN rm /etc/nginx/nginx.conf /etc/nginx/conf.d/default.conf
#从同目录下复制静态资源/配置文件
COPY build_success.html /usr/share/nginx/html/build_success.html
COPY nginx.conf /etc/nginx/nginx.conf