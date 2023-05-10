for i in {0..5} ; do
  rm -rf ./800$i/data;
done
docker stop $(docker ps -a -q) && docker rm $(docker ps -a -q)
docker-compose up -d