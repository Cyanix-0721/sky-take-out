# 使用官方的 Nginx 基础镜像
FROM nginx:latest

# 复制 Nginx 配置文件
COPY conf /etc/nginx/conf.d

# 复制静态资源
COPY html/sky /usr/share/nginx/html/sky

# 复制 contrib 目录下的工具
COPY contrib /etc/nginx/contrib

# 暴露端口
EXPOSE 80

# 启动 Nginx
CMD ["nginx", "-g", "daemon off;"]